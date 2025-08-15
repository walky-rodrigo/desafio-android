package common

sealed class OnResult<out T> {
    data class Success<out T>(val data: T) : OnResult<T>()
    data class Error(val type: ErrorType, val msg: String = "") : OnResult<Nothing>()
    class Loading() : OnResult<Nothing>()
}

fun OnResult.Error.isNetwork(): Boolean {
    return this.type == ErrorType.NETWORK
}
