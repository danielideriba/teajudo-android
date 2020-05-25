package br.com.android.teajudo.data.remote

import androidx.lifecycle.LiveData
import br.com.android.teajudo.data.remote.model.StoreRequest
import com.module.coreapps.api.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreApi {
    @GET(STORES_PATH)
    fun getStores(
        @Query("lat") lat: String,
        @Query("lng") lng: String,
        @Query("distance") distance: Int): LiveData<ApiResponse<StoreRequest>>

    companion object {
        const val STORES_PATH = "/api"
    }
}