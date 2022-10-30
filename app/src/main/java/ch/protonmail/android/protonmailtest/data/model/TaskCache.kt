package ch.protonmail.android.protonmailtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskCache(
    @PrimaryKey val id: String = "",
    val creationDate: String = "",
    val dueDate: String = "",
    val encryptedDescription: String = "",
    val encryptedTitle: String = "",
    val image: String = ""
)