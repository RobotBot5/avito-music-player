package com.robotbot.avito.data.music.sources.base

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.robotbot.avito.data.music.sources.dto.DtoWithError
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> wrapRetrofitExceptions(block: suspend () -> T): T {
    return try {
        val response = block()

        if (response is DtoWithError && response.error != null) {
            throw BackendException(response.error!!.code, response.error!!.message)
        }

        response
    } catch (e: JsonIOException) {
        throw ParseBackendResponseException(e)
    } catch (e: JsonSyntaxException) {
        throw ParseBackendResponseException(e)
    } catch (e: HttpException) {
        throw BackendException(e.code(), "Unknown error")
    } catch (e: IOException) {
        throw ConnectionException(e)
    }
}

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}

class ConnectionException(cause: Throwable) : AppException(cause = cause)

open class BackendException(
    val code: Int,
    message: String
) : AppException(message)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)