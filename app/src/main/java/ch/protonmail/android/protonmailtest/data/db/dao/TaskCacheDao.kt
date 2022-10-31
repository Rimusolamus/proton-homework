package ch.protonmail.android.protonmailtest.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ch.protonmail.android.protonmailtest.data.model.TaskCache

@Dao
interface TaskCacheDao {
    @Query("SELECT * FROM taskcache")
    suspend fun getAll(): List<TaskCache>

    @Query("SELECT * FROM taskcache WHERE id = :id")
    suspend fun getTask(id: String): TaskCache

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(allTasks: List<TaskCache>)

    @Query("DELETE FROM taskcache")
    fun deleteAll()
}