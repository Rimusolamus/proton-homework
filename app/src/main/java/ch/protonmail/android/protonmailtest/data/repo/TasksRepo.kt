package ch.protonmail.android.protonmailtest.data.repo

import android.util.Log
import ch.protonmail.android.protonmailtest.data.db.dao.TaskCacheDao
import ch.protonmail.android.protonmailtest.data.model.Task
import ch.protonmail.android.protonmailtest.data.model.TaskCache
import ch.protonmail.android.protonmailtest.data.networking.ApiHelper
import ch.protonmail.android.protonmailtest.di.CryptoHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TasksRepo @Inject constructor(
    private val apiHelper: ApiHelper,
    private val taskCacheDao: TaskCacheDao,
    private val cryptoHelper: CryptoHelper
) {

    suspend fun getTasks(): Flow<List<Task>?> {
        return flow {
            emit(fetchTasksCached())
            try {
                val result = apiHelper.getTasks()
                //Cache to database if response is successful
                if (result.isSuccessful) {
                    result.body().let { it ->
                        if (it != null) {
                            taskCacheDao.deleteAll()
                            taskCacheDao.insertAll(it.map { task ->
                                encryptTask(task)
                            })
                        }
                    }
                } else {
                    Log.e("TasksRepo", "Error fetching tasks")
                }
                emit(fetchTasksCached())
            } catch (e: Exception) {
                Log.e("TasksRepo", "Retrofit error", e)
            }
        }.flowOn(Dispatchers.IO)
    }

    private fun encryptTask(task: Task): TaskCache {
        return TaskCache(
            id = task.id,
            encryptedTitle = task.encryptedTitle,
            encryptedDescription = task.encryptedDescription,
            image = encryptField(task.image),
            dueDate = encryptField(task.dueDate),
            creationDate = encryptField(task.creationDate)
        )
    }

    private fun decryptField(field: String): String {
        return cryptoHelper.instance.decrypt(field).getOrNull() ?: cryptoHelper.instance.decrypt(
            field
        ).getOrNull() ?: field
    }

    private fun decryptField(taskCache: TaskCache): Task {
        return Task(
            creationDate = decryptField(taskCache.creationDate),
            image = decryptField(taskCache.image),
            encryptedTitle = decryptField(taskCache.encryptedTitle),
            encryptedDescription = decryptField(taskCache.encryptedDescription),
            dueDate = decryptField(taskCache.dueDate),
            id = taskCache.id,
        )
    }

    private fun encryptField(field: String): String {
        return cryptoHelper.instance.encrypt(field).getOrNull() ?: field
    }

    suspend fun fetchTaskCached(taskId: String): Task {
        return decryptField(taskCacheDao.getTask(taskId))
    }

    private suspend fun fetchTasksCached(): List<Task> =
        taskCacheDao.getAll().let { it ->
            it.map {
                Task(
                    creationDate = decryptField(it.creationDate),
                    image = decryptField(it.image),
                    encryptedTitle = decryptField(it.encryptedTitle),
                    encryptedDescription = decryptField(it.encryptedDescription),
                    dueDate = decryptField(it.dueDate),
                    id = it.id,
                )
            }
        }
}
