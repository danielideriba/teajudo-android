package br.com.android.teajudo.ui.maps

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.databinding.FragmentMapsBinding
import br.com.android.teajudo.utils.ScreenUtils
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.module.coreapps.api.Status
import com.module.coreapps.utils.DoubleUtils
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_helping.*
import timber.log.Timber
import javax.inject.Inject


class MapsFragment: BaseFragment(), OnMapReadyCallback {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            MapsViewModel::class.java
        )
    }

    lateinit var binding: FragmentMapsBinding
    lateinit var gMaps: GoogleMap
    lateinit var locationManager : LocationManager

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

            initView()

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
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

    private fun initView() {
        viewModel.getStoresFromApi("-23.5534401","-46.7108107", 5)
        observeViewModel()
    }

    private fun createMultipleMarkers(data: List<StoreEntity>){
        for (i in data.indices) {
            var doubleLag = DoubleUtils.convertStringToDouble(data[i].lat)
            var doubleLng = DoubleUtils.convertStringToDouble(data[i].lng)
            createMarker(gMaps, doubleLag, doubleLng,"TESTE", "TESTE")
        }
    }

    private fun createMarker(
        googleMap: GoogleMap,
        latitude: Double,
        longitude: Double,
        title: String?,
        snippet: String?
    ): Marker? {
        return googleMap.addMarker(
            MarkerOptions()
                .position(LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
        )
    }

    override fun onMapReady(map: GoogleMap?) {
        map.let { map ->
            if (map != null) {
                this.gMaps = map
            }
        }
    }

}