package me.vavra.sportresults.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideRemoteService(
    ): RemoteService {
        return Retrofit.Builder()
            .baseUrl("https://sport-results-654a4-default-rtdb.europe-west1.firebasedatabase.app")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(RemoteService::class.java)
    }

    @Provides
    @Singleton
    fun provideRemoteRepository(service: RemoteService): RemoteRepository {
        return RemoteRepositoryImpl(service)
    }
}