package database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ContactEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val username: String,
    val img: String
)
