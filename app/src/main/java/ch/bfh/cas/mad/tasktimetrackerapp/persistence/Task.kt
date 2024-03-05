package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class Task (
    @PrimaryKey(autoGenerate = true)
    val taskId: Int,  // must be named as taskId (or simply different from widgetTask.id), because otherwise SQL Join between task and widgetTask will build a wrong Task object with id from widgetTask!
    val name: String,
    val projectId: Int
) {
    fun getTaskName(): String {
        return this.name
    }
}
