package br.com.android.teajudo.data.error

import androidx.annotation.IntDef
import br.com.android.teajudo.data.remote.AccessTokenException
import br.com.android.teajudo.data.remote.ExpectationException
import br.com.android.teajudo.data.remote.NoInternetConnectionException
import br.com.android.teajudo.data.remote.UnauthorizedException
import timber.log.Timber

class ErrorResponse(t: Throwable? = null) {
    @ErrorType
    var errorCode: Int

    var errorMessage: String = ""

    init {
        errorCode = when (t) {
            is NoInternetConnectionException -> CONNECTION_ERROR
            is UnauthorizedException -> UNAUTHORIZED_ERROR
            is AccessTokenException -> ACCESS_TOKEN_ERROR
            is ExpectationException -> EXPECTATION_ERROR
            else -> GENERIC_ERROR
        }
        logNewError(t)
        t?.message?.let {
            errorMessage = it
        }
    }

    @IntDef(CONNECTION_ERROR, GENERIC_ERROR, UNAUTHORIZED_ERROR, ACCESS_TOKEN_ERROR)
    @Retention(AnnotationRetention.SOURCE)
    annotation class ErrorType

    private fun logNewError(t: Throwable?) {
        if (t != null) {
            Timber.e(t, "New error generated from throwable.\nError code:%d", errorCode)
        } else {
            Timber.e("New error code:%d", errorCode)
        }
    }

    override fun equals(other: Any?): Boolean {
        return other is ErrorResponse && other.errorCode == errorCode
    }

    override fun hashCode(): Int {
        return errorCode
    }

    companion object {
        const val CONNECTION_ERROR = 1
        const val GENERIC_ERROR = 2
        const val UNAUTHORIZED_ERROR = 3
        const val ACCESS_TOKEN_ERROR = 4
        const val EXPECTATION_ERROR = 5
    }
}