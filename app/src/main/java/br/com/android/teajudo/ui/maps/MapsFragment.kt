package br.com.android.teajudo.ui.maps

import android.app.Activity
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.data.remote.model.Store
import br.com.android.teajudo.databinding.FragmentMapsBinding
import br.com.android.teajudo.utils.Constants
import br.com.android.teajudo.utils.ResourcesUtils
import br.com.android.teajudo.utils.ScreenUtils
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.module.coreapps.api.Status
import com.module.coreapps.helpers.LocationHelper
import com.module.coreapps.utils.DoubleUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_helping.*
import kotlinx.android.synthetic.main.fragment_map_marker_info.view.*
import timber.log.Timber
import javax.inject.Inject


class MapsFragment: BaseFragment(), OnMapReadyCallback, GoogleMap.InfoWindowAdapter {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            MapsViewModel::class.java
        )
    }

    lateinit var binding: FragmentMapsBinding
    lateinit var gMaps: GoogleMap

    var listOfLocations = arrayListOf<String>()

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

        mapFragmentConfig()

        return binding.root
    }

    private fun mapFragmentConfig() {
        val supportMapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        supportMapFragment.getMapAsync { googleMap ->
            googleMap.setOnInfoWindowClickListener { marker ->
                Timber.d("lol")
            }
        }

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
            createMarker(gMaps, doubleLag, doubleLng, data[i].name, data[i].email, data[i].type)
        }
    }

    private fun mapZoomBylatLng(lat: Double, lng: Double){
        val latLng = LatLng(lat, lng)
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(12f)
            .bearing(20f)
            .tilt(0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        gMaps.animateCamera(cameraUpdate)
    }

    private fun createMarker(
        googleMap: GoogleMap,
        latitude: Double,
        longitude: Double,
        title: String?,
        snippet: String?,
        icon: String
    ): Marker? {
        return googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(ResourcesUtils.mapIconBitmap(icon))
        )
    }

    override fun onMapReady(map: GoogleMap?) {
        map.let { map ->
            if (map != null) {
                this.gMaps = map
                context?.let { getLocation(it) }
            }
        }
    }

    private fun getLocation(context: Context) {
        LocationHelper().startListeningUserLocation(context, object: LocationHelper.MyLocationListener {
            override fun onLocationChanged(location: Location) {
                mapZoomBylatLng(location.latitude, location.longitude)

                listOfLocations.addAll(listOf(location.latitude.toString(), location.longitude.toString()))

                if(listOfLocations.size > 0) {
                    viewModel.getStoresFromApi(
                        listOfLocations[0],
                        listOfLocations[1],
                        Constants.DISTANCE_STORES
                    )
                    observeViewModel()
                }
            }
        })
    }

    override fun getInfoContents(marker: Marker?): View {
        var mInfoView = (context as Activity).layoutInflater.inflate(R.layout.fragment_map_marker_info, null)
        var mInfoWindow: Store? = marker?.tag as Store?

        mInfoView.txtMarkerName.text = mInfoWindow?.type


        return mInfoView
    }

    override fun getInfoWindow(p0: Marker?): View {
        Timber.d("infoWindow")
    }
}