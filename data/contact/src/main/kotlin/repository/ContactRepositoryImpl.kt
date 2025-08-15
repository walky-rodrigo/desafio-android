package repository

import common.CacheStrategy
import common.OnResult
import database.dao.ContactDao
import mappers.toContactEntityList
import mappers.toContactList
import mappers.toContactModelList
import model.Contact
import network.safeApiCall
import preferences.DesafioPreferences
import remote.api.ContactApi
import remote_config.RemoteConfig
import javax.inject.Inject

class ContactRepositoryImpl @Inject constructor(
    private val api: ContactApi,
    private val pref: DesafioPreferences,
    private val remoteConfig: RemoteConfig,
    private val dao: ContactDao
) : ContactRepository {

    private var memoryCache: List<Contact> = emptyList()

    init {
        pref.init()
    }

    override suspend fun fetchContacts(strategy: CacheStrategy): OnResult<List<Contact>> {

        when (strategy) {

            CacheStrategy.CacheFirst -> {
                if (memoryCache.isNotEmpty()) return OnResult.Success(data = memoryCache)


                val dbCache = dao.getAll().toContactModelList()

                if (dbCache.isNotEmpty()) {
                    memoryCache = dbCache
                    return OnResult.Success(data = memoryCache)
                }

                return fetchContactsFromApi()
            }

            CacheStrategy.CacheIfIsNotStale -> {
                val expired = isCachedExpired(
                    lastUpdate = lastTimeUpdate(),
                    maxDuration = remoteConfig.getCacheMaxDuration()
                )

                if (expired) clearContactCache()

                if (memoryCache.isNotEmpty()) return OnResult.Success(data = memoryCache)

                return fetchContactsFromApi()
            }

            CacheStrategy.FromApi -> {
                return fetchContactsFromApi()
            }

        }
    }

    override suspend fun fetchContactsFromApi(): OnResult<List<Contact>> {
        return safeApiCall {
            val newContacts = api.getUsers().toContactList()
            clearContactCache()
            memoryCache = newContacts
            saveContacts(newContacts)
            newContacts
        }
    }

    fun isCachedExpired(lastUpdate: Long, maxDuration: Long): Boolean {
        return (System.currentTimeMillis() - lastUpdate) >= maxDuration
    }

    override suspend fun clearContactCache() {
        pref.remove(key = KEY_LAST_UPDATE)
        dao.deleteAll()
        memoryCache = emptyList()
    }

    override suspend fun saveContacts(contacts: List<Contact>) {
        pref.putLong(KEY_LAST_UPDATE, System.currentTimeMillis())
        dao.insertAll(contacts = contacts.toContactEntityList())
    }

    private fun lastTimeUpdate() = pref.getLong(key = KEY_LAST_UPDATE, default = 0L) ?: 0L

    companion object {
        const val KEY_LAST_UPDATE = "last_update"
    }

}