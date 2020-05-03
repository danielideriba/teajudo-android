package br.com.android.teajudo.data.remote

import java.io.IOException

class NoInternetConnectionException(message: String) : IOException(message)

class UnauthorizedException : IOException()

class AccessTokenException: IOException()

class ExpectationException: IOException()