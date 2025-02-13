package com.robotbot.avito.data.music.sources.base

import com.google.gson.JsonIOException
import com.google.gson.JsonSyntaxException
import com.robotbot.avito.common.BackendException
import com.robotbot.avito.common.ConnectionException
import com.robotbot.avito.common.ParseBackendResponseException
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