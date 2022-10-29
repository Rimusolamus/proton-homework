package ch.protonmail.android.protonmailtest.networking

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val api: Api
) : ApiHelper {
    override suspend fun getTasks() = api.getTasks()
}
