package ch.protonmail.android.protonmailtest.data.model

import com.squareup.moshi.Json

data class Task(
    @field:Json(name = "creation_date") val creationDate: String = "",

    @field:Json(name = "due_date") val dueDate: String = "",

    @field:Json(name = "encrypted_description") val encryptedDescription: String = "",

    @field:Json(name = "encrypted_title") val encryptedTitle: String = "",

    val id: String = "", val image: String = ""
)