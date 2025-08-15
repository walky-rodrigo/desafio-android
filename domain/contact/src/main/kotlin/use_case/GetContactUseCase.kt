package use_case

import common.OnResult
import model.Contact

interface GetContactUseCase {

    suspend fun fetchContact(): OnResult<List<Contact>>
}