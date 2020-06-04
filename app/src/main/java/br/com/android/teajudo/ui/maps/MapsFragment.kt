package br.com.android.teajudo.ui.maps

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.databinding.FragmentMapsBinding
import br.com.android.teajudo.databinding.StoreMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.module.coreapps.api.Status
import dagger.android.support.AndroidSupportInjection
import timber.log.Timber
import javax.inject.Inject


class MapsFragment: BaseFragment(){
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            MapsViewModel::class.java
        )
    }

    lateinit var binding: FragmentMapsBinding

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        observeViewModel()

//        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
//        mapFragment?.getMapAsync(callback)
    }

    private fun observeViewModel() {
        viewModel.storeLiveData.observe(
            viewLifecycleOwner,
            Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (it.data.isNullOrEmpty()){
//                        this.markersArray = it.data

                        if (this::binding.isInitialized) {
                            binding.headTitle.text = it.data?.get(0)?.name
                        }
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
                    if (this::binding.isInitialized) {
                        binding.loadingIndicator.start()
                    }
                }
                Status.SERVER_ERROR -> {
                    Timber.d("SERVER ERRO")

                }
            }
        })
    }

    private fun initView() {
        viewModel.getStoresFromApi("-23.5411169","-46.6415725", 5)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        var lag = "-23.5411169".toDouble()
        var lng = "-46.6415725".toDouble()
        val locationMark = LatLng(lag, lng)
        createMarker(googleMap, lag, lng, "Marker in Sydney", "Marker in Sydney")
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationMark))
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

}