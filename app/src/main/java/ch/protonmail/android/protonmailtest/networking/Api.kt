package ch.protonmail.android.protonmailtest.networking

import ch.protonmail.android.protonmailtest.model.Task
import retrofit2.Response
import retrofit2.http.GET

interface Api {
    @GET("tasks.json")
    suspend fun getTasks(): List<Task>
}