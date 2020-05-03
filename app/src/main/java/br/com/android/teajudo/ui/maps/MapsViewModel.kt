package br.com.android.teajudo.ui.maps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.android.teajudo.data.MapsRepository
import javax.inject.Inject

class MapsViewModel @Inject constructor(var mapsRepository: MapsRepository) : ViewModel() {
//    var dataModel: MutableLiveData<BreedImage> = MutableLiveData<BreedImage>()
//
//    fun getOneBreedImage(dogBreed: String){
//        dogsRepository.getBreedImage(dogBreed, object : RepositoryCallback {
//            override fun onSuccess(result: Any?) {
//                val results = result as BreedImage
//                if(results.status == "success") {
//                    dataModel.postValue(results)
//                }
//            }
//
//            override fun onFailure(error: ErrorResponse?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//        })
//
//    }
}