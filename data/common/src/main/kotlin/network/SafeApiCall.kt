package network

import com.google.gson.JsonSyntaxException
import common.ErrorType
import common.OnResult
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

suspend fun <T> safeApiCall(apiCall: suspend () -> T): OnResult<T> {
    return try {

        OnResult.Success(apiCall())

    } catch (e: IOException) {

        OnResult.Error(ErrorType.NETWORK, e.localizedMessage ?: ErrorType.NETWORK.title)

    } catch (e: SocketTimeoutException) {

        OnResult.Error(ErrorType.NETWORK, "Request timed out")

    } catch (e: HttpException) {

        val code = e.code()
        val errorType = when (code) {
            in 400..499 -> ErrorType.CLIENT
            in 500..599 -> ErrorType.SERVER
            else -> ErrorType.UNKNOWN
        }
        OnResult.Error(errorType, e.message())

    } catch (e: JsonSyntaxException) {

        OnResult.Error(ErrorType.PARSE, "Failed to parse response")

    } catch (e: Exception) {

        OnResult.Error(ErrorType.UNKNOWN, e.localizedMessage ?: ErrorType.UNKNOWN.title)
    }
}