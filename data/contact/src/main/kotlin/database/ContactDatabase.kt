package database

import androidx.room.Database
import androidx.room.RoomDatabase
import database.dao.ContactDao
import database.entity.ContactEntity

@Database(entities = [ContactEntity::class], version = 1)
abstract class ContactDatabase : RoomDatabase() {
    abstract fun ContactDao(): ContactDao
}