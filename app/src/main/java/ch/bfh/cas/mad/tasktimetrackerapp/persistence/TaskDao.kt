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
    @Query("SELECT * from task")
    suspend fun getAll(): MutableList<Task>

    // Update

    // Delete
    @Delete
    suspend fun delete(task: Task)
}