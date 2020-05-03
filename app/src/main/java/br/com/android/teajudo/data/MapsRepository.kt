package br.com.android.teajudo.data

import br.com.android.teajudo.data.remote.MapsAPIService
import br.com.android.teajudo.data.source.RepositoryCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executor
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class MapsRepository
@Inject constructor(private val mapsAPIService: MapsAPIService, private val executor: Executor) {

//    fun getBreedImage(dogBreed: String, callback: RepositoryCallback) {
//        dogAPIService.getDogBreedImage(dogBreed).enqueue(object : Callback<BreedImage> {
//            override fun onResponse(call: Call<BreedImage>, response: Response<BreedImage>) {
//                callback.onSuccess(response.body())
//            }
//
//            override fun onFailure(call: Call<BreedImage>, t: Throwable) {
//                callback.onFailure(ErrorResponse(t))
//            }
//
//        })
//    }
}