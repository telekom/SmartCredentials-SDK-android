package de.telekom.identityprovider.exception

class ServiceConnectionException : Exception {
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}