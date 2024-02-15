package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry (
    @PrimaryKey val description: String,
    val taskId: Int,
    val duration: Int
)