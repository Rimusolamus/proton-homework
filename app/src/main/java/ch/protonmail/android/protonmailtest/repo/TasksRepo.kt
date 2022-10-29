package ch.protonmail.android.protonmailtest.repo

import ch.protonmail.android.protonmailtest.networking.ApiHelper
import javax.inject.Inject

class TasksRepo @Inject constructor(
    private val apiHelper: ApiHelper
) {
    suspend fun getTasks() = apiHelper.getTasks()
}
