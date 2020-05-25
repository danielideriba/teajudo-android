package com.module.coreapps.repository

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.module.coreapps.api.ApiResponse
import com.module.coreapps.api.Resource

abstract class NetworkBoundResource<ResultType, RequestType> @MainThread
constructor() {
    private val result = MediatorLiveData<Resource<ResultType>>()

    val asLiveData: LiveData<Resource<ResultType>>
        get() = result

    init {
        result.value = Resource.loading(null)
        val dbSource = this.loadFromDb()
        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)

            if (shouldFetch(data)) {
                try {
                    fetchFromNetwork(dbSource, data)
                } catch (_: Exception) {
                    result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
                }
            } else {
                result.addSource(dbSource) { newData -> setValue(Resource.success(newData)) }
            }
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>, dbResult: ResultType?) {
        val apiResponse = fetchService()

        result.addSource(dbSource) { newData ->
            setValue(Resource.loading(newData))
        }

        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)

            response?.let {
                if (response.isSuccessful) {
                    if (response.isNoContent) {
                        setValue(Resource.noContent())
                    } else {
                        response.body?.let {
                            saveFetchData(it, dbResult)
                            val loaded = loadFromDb()
                            result.addSource(loaded) { newData ->
                                result.removeSource(loaded)
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                } else {
                    onFetchFailed()
                    result.removeSource(dbSource)

                    result.addSource(dbSource) {
                        when (response.code) {
                            404 -> {
                                setValue(Resource.notFound())
                            }
                            in 400..499 -> {
                                setValue(Resource.clientError(response.error))
                            }
                            else -> {
                                setValue(Resource.serverError(response.error))
                            }
                        }
                    }
                }
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<ResultType>) {
        result.value = newValue
    }

    /**
     * Chamado para salvar o resultado da API no banco de dados
     */
    @WorkerThread
    protected abstract fun saveFetchData(item: RequestType, dbResult: ResultType?)

    /**
     * Chamado quando é obtido os dados do banco de dados para decidir se os dados devem ser requisitados do serviço
     */
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    /**
     * Chamado para obter os dados cacheados do banco de dados
     */
    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>

    /**
     * CHamado para criar a chamada de API
     */
    @MainThread
    protected abstract fun fetchService(): LiveData<ApiResponse<RequestType>>

    /**
     * Chamado quando a chamada do serviço falhar. As classes que herdarem talvez precisem resetar a paginaçao, por exemplo
     */
    @MainThread
    protected abstract fun onFetchFailed()

    protected abstract val key: String
}