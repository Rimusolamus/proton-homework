package ch.protonmail.android.protonmailtest.data.networking

import ch.protonmail.android.protonmailtest.data.model.Task
import retrofit2.Response

interface ApiHelper {
    suspend fun getTasks(): Response<List<Task>>
}