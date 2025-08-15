package remote.api

import remote.dto.ContactDto
import retrofit2.http.GET

interface ContactApi {

    @GET("picpay/api/users")
    suspend fun getUsers(): List<ContactDto>
}