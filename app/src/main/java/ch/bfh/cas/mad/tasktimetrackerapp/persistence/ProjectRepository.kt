package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(
    private val projectDao: ProjectDao
) {
    suspend fun getAllProjects(): MutableList<Project> = withContext(Dispatchers.IO) {
        var storedProjects = getStoredProjects()
        if (storedProjects.isNotEmpty()) {
            return@withContext storedProjects
        }
        addNewProject()
        storedProjects = getStoredProjects()
        return@withContext storedProjects
    }

    suspend fun deleteAllProjects() {
        getAllProjects().forEach {
            projectDao.delete(it)
        }
    }

    private suspend fun getStoredProjects(): MutableList<Project> = projectDao.getAll()

    private suspend fun addNewProject()
    {
        val newProject = Project(id = 2, name = "New Test Project 2")
        projectDao.insert(newProject)
    }
}