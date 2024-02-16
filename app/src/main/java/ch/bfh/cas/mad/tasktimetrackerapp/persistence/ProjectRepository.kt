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
        val storedEntries = entryDao.getAllEntriesForProject(projectId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        //return@withContext emptyList<Entry>().toMutableList()
        // ToDo: remove this initial list
        if (projectId == 1)
        {
            return@withContext mutableListOf(Entry(1,"Test entry 1 P1", 1, 100), Entry(2,"Test entry 2 P1", 1, 200), Entry(3,"Test entry 3 P1", 4, 300))
        }
        else
            return@withContext mutableListOf(Entry(1,"Test entry 1 P?", 1, 100), Entry(2,"Test entry 2 P?", 1, 200), Entry(3,"Test entry 3 P?", 4, 300))
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