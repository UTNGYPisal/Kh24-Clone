package com.learn.android.khmer24clone.model.api

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

/**
 * Call to API with Error Handling
 */
suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T): UnhandledResult<T> {
    return withContext(dispatcher) {
        try {
            UnhandledResult.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> UnhandledResult.Error(throwable, 0)
                is HttpException -> {
                    val code = throwable.code()
                    UnhandledResult.Error(throwable, code)
                }
                else -> {
                    UnhandledResult.Error(throwable as? Exception, null)
                }
            }
        }
    }
}



/**
 * Takes any number of Pair<String, Any> and put them in HashMap
 * - The HashMap has default header object
 */
fun buildParams(vararg pairs: Pair<String, Any>): HashMap<String, Any> {
    val unencryptedParams = hashMapOf<String, Any>().apply {
        //put("header", commonHeaderHashMap)
        putAll(pairs)
    }

    return unencryptedParams
}


class BaseResponse<T: Any> {
    val code: Int = 0
    val success: Boolean = false
    val message: String = ""
    val result: T? = null
    val token: String? = null
}
//endregion

//====================================================================================
//region Http Request Result
//====================================================================================
sealed class UnhandledResult<out R> {

    data class Success<out T>(val data: T? = null) : UnhandledResult<T>()

    data class Error(val exception: Exception?, val code: Int?) : UnhandledResult<Nothing>()

    object Loading : UnhandledResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}
//endregion
