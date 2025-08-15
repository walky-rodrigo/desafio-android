package repository

import common.CacheStrategy
import common.OnResult
import model.Contact

interface ContactRepository {

    suspend fun fetchContacts(strategy: CacheStrategy): OnResult<List<Contact>>

    suspend fun clearContactCache()

    suspend fun saveContacts(contacts: List<Contact>)

    suspend fun fetchContactsFromApi(): OnResult<List<Contact>>

}