package br.com.android.teajudo.data

import androidx.lifecycle.LiveData
import br.com.android.teajudo.data.db.dao.AvailableDao
import br.com.android.teajudo.data.db.dao.StoreDao
import br.com.android.teajudo.data.db.dao.StoreDetailsDao
import br.com.android.teajudo.data.db.entities.AvailableEntity
import br.com.android.teajudo.data.db.entities.StoreDetailsEntity
import br.com.android.teajudo.data.db.entities.StoreEntity
import br.com.android.teajudo.data.remote.api.StoreApi
import br.com.android.teajudo.data.remote.VolunteerType
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

    fun getStores(lat: String, lng: String) = dao.getStores()

    fun doStores(
        lat: String,
        lng: String,
        distance: Int
    ): LiveData<Resource<List<StoreEntity>>> {
        return object :
            NetworkBoundResource<List<StoreEntity>, StoreRequest>() {
            override fun saveFetchData(items: StoreRequest, dbResult: List<StoreEntity>?) {
                if (items.data.isNotEmpty()) {
                    var item = items.data
                    for (i in item.indices) {
                        var emailData =
                            if (item[i].email.isNullOrBlank()) "" else item[i].email

                        when(item[i].type) {
                            VolunteerType.STORE.type -> {
                                item[i].options.available.let {
                                    val availableEntity = AvailableEntity(
                                        i,
                                        it?.others,
                                        it?.delivery,
                                        it?.addOthers
                                    )
                                    daoAvailable.insert(availableEntity)
                                }

                                val storeDetailsEntity = StoreDetailsEntity(
                                    i,
                                    item[i].options.owner,
                                    item[i].options.veracidade,
                                    item[i].options.marketGarden,
                                    false,
                                    false,
                                    false,
                                    false,
                                    ""
                                )
                                daoDetails.insert(storeDetailsEntity)
                            }
                            VolunteerType.VOLUNTEER.type -> {
                                val storeDetailsEntity = StoreDetailsEntity(
                                    i,
                                    "",
                                    false,
                                    false,
                                    item[i].options.food,
                                    item[i].options.talk,
                                    item[i].options.market,
                                    item[i].options.health,
                                    ""
                                )
                                daoDetails.insert(storeDetailsEntity)
                            }
                            VolunteerType.USERTYPE.type -> {
                                val storeDetailsEntity = StoreDetailsEntity(
                                    i,
                                    "",
                                    false,
                                    false,
                                    item[i].options.food,
                                    false,
                                    item[i].options.market,
                                    false,
                                    item[i].options.others
                                )
                                daoDetails.insert(storeDetailsEntity)
                            }
                        }

                        val storeEntity = StoreEntity(
                            i,
                            item[i].name,
                            emailData,
                            item[i].phone,
                            item[i].whatsapp,
                            item[i].lat,
                            item[i].lng,
                            item[i].type
                        )

                        dao.insert(storeEntity)
                    }

                }
            }

            override fun shouldFetch(data: List<StoreEntity>?): Boolean {
                return true
            }

            override fun loadFromDb(): LiveData<List<StoreEntity>> {
                return dao.getStores()
            }

            override fun fetchService(): LiveData<ApiResponse<StoreRequest>>{
                return storeApi.getStores(lat, lng, distance)
            }

            override fun onFetchFailed() {
                return rateLimiter.reset(key)
            }

            override val key: String
                get() = "$lat"
        }.asLiveData
    }
}