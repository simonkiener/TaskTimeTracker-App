package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TaskDao {
    // Create
    @Insert
    suspend fun insert(task: Task)

    // Read
    @Query("Select * FROM task WHERE taskId = :taskId")
    suspend fun getTask(taskId: Int): Task

    @Query("Select * FROM task WHERE projectId = :projectId")
    suspend fun getTasksForProject(projectId: Int): MutableList<Task>

    @Query("SELECT * FROM task")
    suspend fun getAll(): MutableList<Task>

    // Update

    // Delete
    @Query("DELETE FROM task")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(task: Task)
}