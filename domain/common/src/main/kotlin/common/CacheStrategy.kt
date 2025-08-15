package common

sealed class CacheStrategy {
    object CacheFirst: CacheStrategy()
    object CacheIfIsNotStale: CacheStrategy()
    object FromApi: CacheStrategy()
}