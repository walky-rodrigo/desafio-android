package use_case

import common.CacheStrategy
import common.OnResult
import model.Contact
import repository.ContactRepository
import javax.inject.Inject

class GetContactUseCaseImpl @Inject constructor(private val repository: ContactRepository) :
    GetContactUseCase {

    override suspend fun fetchContact(): OnResult<List<Contact>> {
        return repository.fetchContacts(strategy = CacheStrategy.CacheIfIsNotStale)
    }
}