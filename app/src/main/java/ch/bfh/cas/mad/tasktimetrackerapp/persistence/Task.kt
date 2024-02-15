package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "task")
data class Task (
    @PrimaryKey val id: Int,
    val name: String,
    val projectId: Int
)
