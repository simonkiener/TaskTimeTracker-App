package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "widgetTask")
data class WidgetTask (
    @PrimaryKey val id: Int,
    val taskId: Int
)