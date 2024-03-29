package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectRepository(
    private val projectDao: ProjectDao,
    private val entryDao: EntryDao
) {
    suspend fun getAllProjects(): MutableList<Project> = withContext(Dispatchers.IO) {
        val storedProjects = projectDao.getAll()
        if (storedProjects.isNotEmpty()) {
            return@withContext storedProjects
        }

        return@withContext emptyList<Project>().toMutableList()
    }

    suspend fun getEntriesForProject(projectId: Int): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getEntriesForProject(projectId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getProject(projectId: Int):Project {
        return projectDao.getProject(projectId)
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