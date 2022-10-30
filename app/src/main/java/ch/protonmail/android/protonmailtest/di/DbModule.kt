package ch.protonmail.android.protonmailtest.di

import android.content.Context
import androidx.room.Room
import ch.protonmail.android.protonmailtest.data.db.AppDatabase
import ch.protonmail.android.protonmailtest.data.db.dao.TaskCacheDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Provides
    fun provideChannelDao(appDatabase: AppDatabase): TaskCacheDao {
        return appDatabase.taskCacheDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "tasks"
        ).build()
    }
}