package ch.protonmail.android.protonmailtest.model

import kotlinx.serialization.SerialName

data class Task(
    @SerialName("creation_date")
    val creationDate: String,

    @SerialName("due_date")
    val dueDate: String,

    @SerialName("encrypted_description")
    val encryptedDescription: String,

    @SerialName("encrypted_title")
    val encryptedTitle: String,

    val id: String,
    val image: String
)