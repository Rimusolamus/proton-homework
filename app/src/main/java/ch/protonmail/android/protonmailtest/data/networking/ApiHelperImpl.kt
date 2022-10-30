package ch.protonmail.android.protonmailtest.data.networking

import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val api: Api
) : ApiHelper {
    override suspend fun getTasks() = api.getTasks()
}
