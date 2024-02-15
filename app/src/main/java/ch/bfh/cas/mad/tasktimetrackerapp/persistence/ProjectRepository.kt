package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(
    private val projectDao: ProjectDao
) {
    suspend fun getAllProjects(): MutableList<Project> = withContext(Dispatchers.IO) {
        val storedProjects = projectDao.getAll()
        if (storedProjects.isNotEmpty()) {
            return@withContext storedProjects
        }

        return@withContext emptyList<Project>().toMutableList()
    }

    suspend fun deleteAllProjects() {
        getAllProjects().forEach {
            projectDao.delete(it)
        }
    }

    suspend fun addProject(project: Project)
    {
        projectDao.insert(project = project)
    }
}