package ch.protonmail.android.protonmailtest.data.networking

import ch.protonmail.android.protonmailtest.data.model.Task
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("tasks.json")
    suspend fun getTasks(): Response<List<Task>>
}