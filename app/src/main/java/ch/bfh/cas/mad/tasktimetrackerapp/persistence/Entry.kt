package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "entry")
data class Entry (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val taskId: Int,
    val timeStamp: Long
)