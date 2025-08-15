package di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import network.CreateOkHttpClient
import network.CreateRetrofit
import okhttp3.OkHttpClient
import preferences.DesafioPreferences
import remote_config.RemoteConfig
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataCommonModule {

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit {
        return CreateRetrofit.factory(client)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return CreateOkHttpClient.factory()
    }

    @Provides
    fun providesAppSharedPreferences(context: Application): DesafioPreferences {
        return DesafioPreferences(context)
    }

    @Singleton
    @Provides
    fun providesRemoteConfig(): RemoteConfig {
        return RemoteConfig()
    }
}