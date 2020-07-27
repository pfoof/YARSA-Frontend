package com.furfel.yarsa_frontend

/**
 * A generic class that holds a result success w/ data or an error exception.
 */
open class Result<T>  // hide the private constructor to limit subclass types (Success, Error)
private constructor() {
    // Success sub-class
    class Success<T>(val data: T) : Result<T>()

    // Error sub-class
    class Error<T>(val error: Exception) :
        Result<T>()

    override fun toString(): String {
        if (this is Success<*>) {
            val success =
                this as Success<*>
            return "Success[data=" + success.data.toString() + "]"
        } else if (this is Error) {
            val error =
                this as Error
            return "Error[exception=" + error.error.toString() + "]"
        }
        return ""
    }
}