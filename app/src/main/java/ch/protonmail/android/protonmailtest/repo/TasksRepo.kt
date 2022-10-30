package ch.protonmail.android.protonmailtest.repo

import ch.protonmail.android.protonmailtest.di.CryptoHelper
import ch.protonmail.android.protonmailtest.networking.ApiHelper
import javax.inject.Inject

class TasksRepo @Inject constructor(
    private val apiHelper: ApiHelper,
    private val cryptoHelper: CryptoHelper
) {
    suspend fun getTasks() = apiHelper.getTasks()
}
