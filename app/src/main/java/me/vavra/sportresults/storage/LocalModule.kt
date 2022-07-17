package me.vavra.sportresults.storage

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideSportResultsDao(
        @ApplicationContext context: Context
    ): SportResultDao {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "sport-results"
        ).build().sportResultDao()
    }

    @Provides
    @Singleton
    fun provideLocalRepository(dao: SportResultDao): LocalRepository {
        return LocalRepositoryImpl(dao)
    }
}