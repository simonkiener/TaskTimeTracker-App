package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "project")
data class Project (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String
)