package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProjectDao {
    // Create
    @Insert
    suspend fun insert(project: Project)

    // Read
    @Query("SELECT * FROM project")
    suspend fun getAll(): MutableList<Project>

    // Update

    // Delete
    @Delete
    suspend fun delete(project: Project)

    @Query("DELETE FROM project")
    suspend fun deleteAll()

    // ToDo: check if that would work
    @Delete
    suspend fun deleteAll(projects: List<Project>)
}