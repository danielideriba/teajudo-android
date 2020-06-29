package br.com.android.teajudo.ui.maps

import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.AvailableEntity
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.data.remote.model.Store
import br.com.android.teajudo.data.remote.model.StoreDetails
import br.com.android.teajudo.databinding.FragmentMapsBinding
import br.com.android.teajudo.ui.maps.adapters.CustomInfoWindowGoogleMap
import br.com.android.teajudo.utils.Constants
import br.com.android.teajudo.utils.ResourcesUtils
import br.com.android.teajudo.utils.ScreenUtils
import br.com.grupofleury.core.utils.DialogUtils
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.module.coreapps.api.Status
import com.module.coreapps.utils.DoubleUtils
import com.module.coreapps.utils.LocationManagerUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_helping.*
import timber.log.Timber
import javax.inject.Inject

class MapsFragment: BaseFragment(),
    OnMapReadyCallback,
    GoogleMap.OnMapLongClickListener,
    LocationListener,
    GoogleApiClient.ConnectionCallbacks,
    GoogleApiClient.OnConnectionFailedListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            MapsViewModel::class.java
        )
    }

    lateinit var binding: FragmentMapsBinding
    lateinit var gMaps: GoogleMap
    lateinit var marker: Marker
    lateinit var customInfoWindow: CustomInfoWindowGoogleMap
    var listOfLocations = arrayListOf<String>()

    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private val UPDATE_INTERVAL: Long = 10000
    private val FASTEST_INTERVAL: Long = 5000

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
            binding =
                DataBindingUtil.inflate(
                    inflater,
                    R.layout.fragment_maps,
                    container,
                    false
                )

        context?.let { setupLocations(it) }
        mapFragmentConfig()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        googleApiClient.connect()
    }

    override fun onStop() {
        super.onStop()
        googleApiClient.disconnect()
    }

    private fun setupLocations(context: Context){
        if(LocationManagerUtils.isLocationEnabled(context)){
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
            locationRequest = LocationRequest()
            locationRequest.interval = UPDATE_INTERVAL
            locationRequest.fastestInterval = FASTEST_INTERVAL
            locationRequest.smallestDisplacement = 170f
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

            locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return
                    if (locationResult.locations.isNotEmpty()) {
                        getLocation(locationResult.lastLocation)
                    }
                }
            }

            googleApiClient = GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build()
        } else {
            var dialog = DialogUtils.simpleDialog(context, "ATENCAO", "ERRO")
            dialog.setPositiveButton("OK") { dialog, which ->
                Timber.d("Ok, we change the app background.")
            }
            dialog.create()
        }
    }

    private fun mapFragmentConfig() {
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        linearLayoutBackground.layoutParams.height = ScreenUtils.getScreenHeightInDP(view.context)
    }

    private fun observeViewModel() {
        viewModel.storeLiveData.observe(
            viewLifecycleOwner,
            Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data.let {currentData ->
                        if(currentData?.size!! > 0)
                        createMultipleMarkers(currentData)
                    }
                }
                Status.NO_CONTENT,
                Status.NOT_FOUND -> {
                    Timber.d("ERRO")
                }
                Status.CLIENT_ERROR -> {
                    Timber.d("ERRO")
                }
                Status.LOADING -> {
                    binding.loadingIndicator.start()
                }
                Status.SERVER_ERROR -> {
                    Timber.d("SERVER ERRO")

                }
            }
        })
    }

    private fun createMultipleMarkers(data: List<StoreEntity>){
        for (i in data.indices) {
            var doubleLag = DoubleUtils.convertStringToDouble(data[i].lat)
            var doubleLng = DoubleUtils.convertStringToDouble(data[i].lng)
            createMarker(gMaps, doubleLag, doubleLng, data[i].type, data[i])
        }
    }

    private fun mapZoomBylatLng(latLng: LatLng?){
        if(latLng != null) {
            val cameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(12f)
                .bearing(20f)
                .tilt(0f)
                .build()
            val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
            gMaps.animateCamera(cameraUpdate)
        }
    }

    private fun createMarker(
        googleMap: GoogleMap,
        latitude: Double,
        longitude: Double,
        icon: String,
        tagDescrition: StoreEntity
    ): Marker? {
        marker = googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(latitude, longitude))
                            .anchor(0.5f, 0.5f)
                            .icon(ResourcesUtils.mapIconBitmap(icon))
                    )

        marker.tag = tagDescrition

        return marker
    }

    override fun onMapReady(map: GoogleMap?) {
        map.let { map ->
            if (map != null) {
                gMaps = map
                this.gMaps.setOnMapLongClickListener(this)
                this.gMaps.isMyLocationEnabled = true

                context?.let {
                    customInfoWindow = CustomInfoWindowGoogleMap(it)
                    gMaps.setInfoWindowAdapter(customInfoWindow)

                    gMaps.setOnInfoWindowClickListener { marker ->
                        if(marker.isInfoWindowShown){
                            var currentTag = marker.tag as StoreEntity
                            getStoreByIdAndSendTo(it, currentTag, marker)
                        }
                    }
                }
            }
        }
    }

    // I should fix that functionality =/
    private fun getStoreByIdAndSendTo(
        context: Context,
        tag: StoreEntity,
        marker: Marker
    ) {
        viewModel.getStoreDetails(tag.idStore)
        viewModel.getAvailable(tag.idStore)

        viewModel.storeByIdLiveData.observe(
            viewLifecycleOwner,
            Observer { storeById ->
                if(storeById.size > 0 && marker.isInfoWindowShown) {
                    viewModel.availableByIdLiveData.observe(
                        viewLifecycleOwner,
                        Observer { availableById ->
                            var availableEntity: AvailableEntity = if(availableById.isNotEmpty()) {
                                    availableById[0]
                                } else  {
                                    AvailableEntity(0, "", false, false)
                                }

                            marker.hideInfoWindow()
                            MapInfoStoreDetail.start(context, tag, storeById[0], availableEntity)
                        }
                    )
                }
        })
    }

    private fun getLocation(location: Location?) {
        binding.loadingIndicator.start()

        location?.let {
            mapZoomBylatLng(LatLng(it.latitude, it.longitude))

            listOfLocations.addAll(
                listOf(
                    it.latitude.toString(),
                    it.longitude.toString()
                )
            )

            if (listOfLocations.size > 0) {
                viewModel.getStoresFromApi(
                    listOfLocations[0],
                    listOfLocations[1],
                    Constants.DISTANCE_STORES
                )

                observeViewModel()
            }
        }
    }

    override fun onMapLongClick(latLng: LatLng?) {
        mapZoomBylatLng(latLng)
        this.marker.showInfoWindow()
    }

    override fun onLocationChanged(location: Location?) {
        getLocation(location)
    }

    override fun onConnected(bundle: Bundle?) {
        context?.let {
            startLocationUpdates(it)
        }
    }

    override fun onConnectionSuspended(int: Int) {}

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
        Timber.d("Connection Failed--"+connectionResult.toString())
    }

    private fun startLocationUpdates(context: Context) {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )

        fusedLocationClient.requestLocationUpdates(locationRequest, null)
    }

    private fun stopLocationUpdates(context: Context) {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}