package di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import database.ContactDatabase
import preferences.DesafioPreferences
import remote.api.ContactApi
import remote_config.RemoteConfig
import repository.ContactRepository
import repository.ContactRepositoryImpl
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataContactModule {

    @Provides
    @Singleton
    fun provideContactRepository(
        retrofit: Retrofit,
        pref: DesafioPreferences,
        remoteConfig: RemoteConfig,
        contactDatabase: ContactDatabase
    ): ContactRepository {
        return ContactRepositoryImpl(
            api = retrofit.create(ContactApi::class.java),
            pref = pref,
            remoteConfig = remoteConfig,
            dao = contactDatabase.ContactDao()
        )
    }

    @Singleton
    @Provides
    fun providesDatabase(context: Application): ContactDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = ContactDatabase::class.java,
            name = "contact-database"
        ).build()
    }
}