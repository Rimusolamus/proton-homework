package ch.protonmail.android.protonmailtest.data.repo

import ch.protonmail.android.protonmailtest.data.db.dao.TaskCacheDao
import ch.protonmail.android.protonmailtest.data.model.Task
import ch.protonmail.android.protonmailtest.data.model.TaskCache
import ch.protonmail.android.protonmailtest.data.networking.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class TasksRepo @Inject constructor(
    private val apiHelper: ApiHelper,
    private val taskCacheDao : TaskCacheDao
) {
    // suspend fun getTasks() {
    //     apiHelper.getTasks()
    // }
    suspend fun getTasks(): Flow<List<Task>?> {
        return flow {
            emit(fetchTasksCached())
            val result = apiHelper.getTasks()

            //Cache to database if response is successful
            if (result.isSuccessful) {
                result.body().let { it ->
                    taskCacheDao.deleteAll()
                    if (it != null) {
                        taskCacheDao.insertAll(it.map {
                            TaskCache(
                                id = it.id,
                                image = it.image,
                                encryptedTitle = it.encryptedTitle,
                                encryptedDescription = it.encryptedDescription,
                                dueDate = it.dueDate,
                                creationDate = it.creationDate
                            )
                        })
                    }
                }
            }
            emit(result.body())
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun fetchTasksCached(): List<Task> =
        taskCacheDao.getAll().let { it ->
            it.map {
                Task(
                    id = it.id,
                    image = it.image,
                    encryptedTitle = it.encryptedTitle,
                    encryptedDescription = it.encryptedDescription,
                    dueDate = it.dueDate,
                    creationDate = it.creationDate
                )
            }
        }
}
