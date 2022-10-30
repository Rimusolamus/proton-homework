package ch.protonmail.android.protonmailtest.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import ch.protonmail.android.protonmailtest.data.db.dao.TaskCacheDao
import ch.protonmail.android.protonmailtest.data.model.TaskCache

@Database(entities = [TaskCache::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskCacheDao(): TaskCacheDao
}