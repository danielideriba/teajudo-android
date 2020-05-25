package com.module.coreapps.api

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 * I use this class with NetworkBoundResource cause i want to handle the states : Loading , Success, Error
 */
data class Resource<T> constructor(
    val status: Status,
    val data: T?,
    val error: Throwable?
) {
    companion object {

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> noContent(): Resource<T> {
            return Resource(Status.NO_CONTENT, null, null)
        }

        fun <T> notFound(): Resource<T> {
            return Resource(Status.NOT_FOUND, null, null)
        }

        fun <T> clientError(exception: Throwable?): Resource<T> {
            return Resource(Status.CLIENT_ERROR, null, exception)
        }

        fun <T> serverError(exception: Throwable?): Resource<T> {
            return Resource(Status.SERVER_ERROR, null, exception)
        }
    }
}