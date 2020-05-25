package br.com.android.teajudo.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.android.teajudo.BaseFragment
import br.com.android.teajudo.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.module.coreapps.api.Status
import timber.log.Timber
import javax.inject.Inject

class MapsFragment: BaseFragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy {
        ViewModelProvider(requireActivity(), viewModelFactory).get(
            MapsViewModel::class.java
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewActions()
    }

    private fun viewActions(){
        viewModel.getStores("-23.5411169","-46.6415725", 20)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.storeLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Timber.d("---"+it.data)
                }
                Status.NO_CONTENT,
                Status.NOT_FOUND -> {
                    Timber.d("ERRO")

                }
                Status.CLIENT_ERROR -> {
                    Timber.d("ERRO")
                }
                Status.LOADING -> {
                    Timber.d("LOADING")
                }
                Status.SERVER_ERROR -> {
                    Timber.d("SERVER ERRO")

                }
            }
        })
    }

    private val callback = OnMapReadyCallback { googleMap ->
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}