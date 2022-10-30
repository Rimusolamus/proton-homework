package ch.protonmail.android.protonmailtest.data.model

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("creation_date") val creationDate: String = "",

    @SerializedName("due_date") val dueDate: String = "",

    @SerializedName("encrypted_description") val encryptedDescription: String = "",

    @SerializedName("encrypted_title") val encryptedTitle: String = "",

    val id: String = "", val image: String = ""
)