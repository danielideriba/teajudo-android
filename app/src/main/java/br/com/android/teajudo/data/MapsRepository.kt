package br.com.android.teajudo.data

import androidx.lifecycle.LiveData
import br.com.android.teajudo.data.db.dao.AvailableDao
import br.com.android.teajudo.data.db.dao.StoreDao
import br.com.android.teajudo.data.db.dao.StoreDetailsDao
import br.com.android.teajudo.data.db.entities.AvailableEntity
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.data.remote.StoreApi
import br.com.android.teajudo.data.remote.model.StoreRequest
import com.module.coreapps.api.ApiResponse
import com.module.coreapps.api.Resource
import com.module.coreapps.base.shouldFetch
import com.module.coreapps.repository.NetworkBoundResource
import com.module.coreapps.utils.RateLimiter
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MapsRepository @Inject constructor(
    val dao: StoreDao,
    val daoDetails: StoreDetailsDao,
    val daoAvailable: AvailableDao,
    val storeApi: StoreApi
) {
    private val rateLimiter = RateLimiter<String>(1, TimeUnit.HOURS)

    fun getStoreByLatLng(lat: String, lng: String) = dao.getStoreByLatLng(lat, lng)

    fun doStores(lat: String, lng: String, distance: Int): LiveData<Resource<StoreEntity>> {
        return object :
            NetworkBoundResource<StoreEntity, StoreRequest>() {
            override fun saveFetchData(item: StoreRequest, dbResult: StoreEntity?) {

                if(item.data!!.isNotEmpty()) {
                    var emailData = if (item.data[0].email.isNullOrBlank()) "" else item.data[0].email

                    val storeEntity = StoreEntity(
                        0,
                        item.data[0].name,
                        emailData,
                        item.data[0].phone,
                        item.data[0].whatsapp,
                        item.data[0].lat,
                        item.data[0].lng,
                        item.data[0].type
                    )
                    val storeDetailsEntity = StoreDetailsEntity(
                        0,
                        item.data[0].options.owner,
                        item.data[0].options.veracidade,
                        item.data[0].options.marketGarden
                    )

                    val availableEntity = AvailableEntity(
                        0,
                        item.data[0].options.available.others,
                        item.data[0].options.available.delivery,
                        item.data[0].options.available.addOthers
                    )

                    dao.insert(storeEntity)
                    daoDetails.insert(storeDetailsEntity)
                    daoAvailable.insert(availableEntity)
                }
            }

            override fun shouldFetch(data: StoreEntity?): Boolean {
                return lat.isNotEmpty() && lng.isNotEmpty() && data.shouldFetch() && rateLimiter.shouldFetch(key)
            }

            override fun loadFromDb(): LiveData<StoreEntity> {
                return dao.getStoreByLatLng(lat, lng)
            }

            override fun fetchService(): LiveData<ApiResponse<StoreRequest>> {
                return storeApi.getStores(lat, lng, distance)
            }

            override fun onFetchFailed() {
                return rateLimiter.reset(key)
            }

            override val key: String
                get() = "$distance"

        }.asLiveData

    }
}