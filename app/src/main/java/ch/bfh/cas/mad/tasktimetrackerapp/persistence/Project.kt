package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project")
data class Project (
    val name: String,
    @PrimaryKey(autoGenerate = true) val id: Int
)