package remote.dto

import com.google.gson.annotations.SerializedName

data class ContactDto(
    @SerializedName("img") val img: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("id") val id: Int ?= null,
    @SerializedName("username") val username: String? = null
)