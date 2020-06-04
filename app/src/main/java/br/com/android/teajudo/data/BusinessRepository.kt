package br.com.android.teajudo.data

import br.com.android.teajudo.data.remote.api.StoreApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BusinessRepository
@Inject constructor(private val storeApi: StoreApi) {

}