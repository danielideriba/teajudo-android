package com.module.coreapps.exceptions

class ServerErrorException : Exception{
    constructor() : super()
    @Suppress("unused")
    constructor(message: String?) : super(message)
}