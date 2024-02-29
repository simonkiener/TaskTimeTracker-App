package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WidgetTaskDao {
    // Create
    @Insert
    suspend fun insert(widgetTask: WidgetTask)

    // Read
    @Query("SELECT * FROM widgetTask")
    suspend fun getAllWidgetTasks(): MutableList<WidgetTask>

    @Query("SELECT taskId FROM widgetTask WHERE id = :widgetTaskId")
    suspend fun getTaskIdForId(widgetTaskId: Int): Int

    @Query("SELECT * FROM task JOIN widgetTask ON task.id = taskId WHERE widgetTask.id = :widgetTaskId")
    suspend fun getTaskForId(widgetTaskId: Int): MutableList<Task>

    // Update
    @Query("UPDATE widgetTask SET taskId = :taskId WHERE id = :widgetTaskId")
    suspend fun setTaskForId(widgetTaskId: Int, taskId: Int)

    // Delete
    @Query("DELETE FROM widgetTask")
    suspend fun deleteAll()
}