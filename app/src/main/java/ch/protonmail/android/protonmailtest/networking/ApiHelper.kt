package ch.protonmail.android.protonmailtest.networking

import ch.protonmail.android.protonmailtest.model.Task

interface ApiHelper {
    suspend fun getTasks(): List<Task>
}