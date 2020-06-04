package br.com.android.teajudo.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import br.com.android.teajudo.data.MapsRepository
import br.com.android.teajudo.data.db.entities.StoreEntity
import com.module.coreapps.api.Resource
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    val mapsRepository: MapsRepository
) : ViewModel() {
    var storeLiveData: LiveData<Resource<List<StoreEntity>>> = MutableLiveData()

    private var lat: String = ""
    private var lng: String = ""
    private var distance: Int = 0

    private var shouldFetch = false
    private var fetchStore: MutableLiveData<FetchData> = MutableLiveData(
        FetchData(
            "",
            "",
            0
        )
    )

    init {
        storeLiveData = Transformations.switchMap(fetchStore) {
            mapsRepository.doStores(
                it.lat,
                it.lng,
                it.distance
            )
        }
    }

    fun getStoresFromApi(lat: String, lng: String, distance: Int){
        this.lat = lat
        this.lng = lng
        this.distance = distance

        shouldFetch = this.lat != lat
        fetchStore.postValue(FetchData(lat, lng, distance))
    }

    private data class FetchData(
        val lat: String,
        val lng: String,
        val distance: Int
    ) {
        fun shouldFetch(
            lat: String
        ): Boolean {
            return this.lat != lat
                    || this.lng != lng
                    || this.distance != distance
        }
    }

    private fun MutableLiveData<FetchData>.update(lat: String, lng: String, distance: Int) {
        val newFetchData = FetchData(lat, lng, distance)
        this.postValue(newFetchData)
    }
}