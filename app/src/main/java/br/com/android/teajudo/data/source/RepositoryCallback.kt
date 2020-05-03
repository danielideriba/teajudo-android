package br.com.android.teajudo.data.source

import br.com.android.teajudo.data.error.ErrorResponse

interface RepositoryCallback {
    fun onSuccess(result: Any? = null)
    fun onFailure(error: ErrorResponse? = ErrorResponse())
}