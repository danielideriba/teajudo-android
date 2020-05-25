package br.com.android.teajudo.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.android.teajudo.data.MapsRepository
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.data.error.ErrorResponse
import com.module.coreapps.api.Resource
import javax.inject.Inject

class MapsViewModel
@Inject constructor(
    val mapsRepository: MapsRepository
) : ViewModel() {
    var storeLiveData: LiveData<Resource<StoreEntity>> = MutableLiveData()

    private var lat: String = ""
    private var lng: String = ""
    private var distance: Int = 0

    private var fetchStore: MutableLiveData<String> = MutableLiveData()

    init {
        storeLiveData = Transformations.switchMap(fetchStore) {
            mapsRepository.doStores(lat,lng, distance)
        }
    }

    fun getStores(lat: String, lng: String, distance: Int){
        this.lat = lat
        this.lng = lng
        this.distance = distance

        fetchStore.postValue("$lat$lng$distance")
    }
}