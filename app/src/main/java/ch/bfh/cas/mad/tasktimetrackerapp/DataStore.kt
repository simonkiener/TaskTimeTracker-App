package ch.bfh.cas.mad.tasktimetrackerapp

import ch.bfh.cas.mad.tasktimetrackerapp.Entities.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.Entities.Project
import ch.bfh.cas.mad.tasktimetrackerapp.Entities.Task

object DataStore {
    val projects = mutableListOf(
        Project("Projekt 1", 1),
        Project("Projekt 2", 2),
        Project("Projekt 3", 3),
        Project("Projekt 4", 4),
        Project("Projekt 5", 5),
        Project("Projekt 6", 6)
    )

    val tasks = mutableListOf(
        Task(id = 1,name = "Task 1", projectId = 1),
        Task(id = 2,name = "Task 2", projectId = 1),
        Task(id = 3,name = "Task 3", projectId = 2),
        Task(id = 4,name = "Task 4", projectId = 3),
        Task(id = 5,name = "Task 5", projectId = 3),
        Task(id = 6,name = "Task 6", projectId = 3),
        Task(id = 7,name = "Task 7", projectId = 3),
        Task(id = 8,name = "Task 8", projectId = 3),
        Task(id = 9,name = "Task 9", projectId = 4),
        Task(id = 10,name = "Task 10", projectId = 4),
        Task(id = 11,name = "Task 11", projectId = 4),
        Task(id = 12,name = "Task 12", projectId = 5),
        Task(id = 13,name = "Task 13", projectId = 5),
        Task(id = 14,name = "Task 14", projectId = 5),
        Task(id = 15,name = "Task 15", projectId = 6),
        Task(id = 16,name = "Task 16", projectId = 6)
    )

    val entries = mutableListOf(
        Entry(taskId = 1, description = "Entry 1", duration = 60),
        Entry(taskId = 1, description = "Entry 2", duration = 120),
        Entry(taskId = 2, description = "Entry 3", duration = 90),
        Entry(taskId = 3, description = "Entry 4", duration = 30),
        Entry(taskId = 3, description = "Entry 5", duration = 45),
        Entry(taskId = 3, description = "Entry 6", duration = 60),
        Entry(taskId = 4, description = "Entry 7", duration = 120),
        Entry(taskId = 4, description = "Entry 8", duration = 90),
        Entry(taskId = 5, description = "Entry 9", duration = 30),
        Entry(taskId = 5, description = "Entry 10", duration = 45),
        Entry(taskId = 5, description = "Entry 11", duration = 60),
        Entry(taskId = 6, description = "Entry 12", duration = 120),
        Entry(taskId = 6, description = "Entry 13", duration = 90),
        Entry(taskId = 6, description = "Entry 14", duration = 30),
        Entry(taskId = 6, description = "Entry 15", duration = 45),
        Entry(taskId = 7, description = "Entry 16", duration = 60),
        Entry(taskId = 7, description = "Entry 17", duration = 120),
        Entry(taskId = 7, description = "Entry 18", duration = 90),
        Entry(taskId = 8, description = "Entry 19", duration = 30),
        Entry(taskId = 8, description = "Entry 20", duration = 45),
        Entry(taskId = 8, description = "Entry 21", duration = 60),
        Entry(taskId = 9, description = "Entry 22", duration = 120),
        Entry(taskId = 9, description = "Entry 23", duration = 90),
        Entry(taskId = 9, description = "Entry 24", duration = 30),
        Entry(taskId = 10, description = "Entry 25", duration = 45),
        Entry(taskId = 10, description = "Entry 26", duration = 60),
        Entry(taskId = 11, description = "Entry 27", duration = 120),
        Entry(taskId = 11, description = "Entry 28", duration = 90),
        Entry(taskId = 11, description = "Entry 29", duration = 30),
        Entry(taskId = 12, description = "Entry 30", duration = 45),
        Entry(taskId = 12, description = "Entry 31", duration = 60),
        Entry(taskId = 13, description = "Entry 32", duration = 120),
        Entry(taskId = 13, description = "Entry 33", duration = 90),
        Entry(taskId = 13, description = "Entry 34", duration = 30),
        Entry(taskId = 14, description = "Entry 35", duration = 45),
        Entry(taskId = 14, description = "Entry 36", duration = 60),
        Entry(taskId = 15, description = "Entry 37", duration = 120),
        Entry(taskId = 15, description = "Entry 38", duration = 90),
        Entry(taskId = 15, description = "Entry 39", duration = 30),
        Entry(taskId = 16, description = "Entry 40", duration = 45),
        Entry(taskId = 16, description = "Entry 41", duration = 60)
    )

    fun getEntriesForTask(taskId: Int): List<Entry> {
        return entries.filter { it.taskId == taskId }
    }

    fun getTasksForProject(projectId: Int): List<Task> {
        return tasks.filter { it.projectId == projectId }
    }

    fun getProjectName(projectId: Int): String {
        return projects.find { it.id == projectId }?.name ?: ""
    }

    fun getTaskName(taskId: Int): String {
        return tasks.find { it.id == taskId }?.name ?: ""
    }

    fun getProjectForTask(taskId: Int): Project {
        val task = tasks.find { it.id == taskId }
        return projects.find { it.id == task?.projectId } ?: Project("", 0)
    }

    fun getEntriesForProject(projectId: Int): List<Entry> {
        val tasksForProject = getTasksForProject(projectId)
        val entriesForProject = mutableListOf<Entry>()
        tasksForProject.forEach { task ->
            entriesForProject.addAll(getEntriesForTask(task.id))
        }
        return entriesForProject
    }
}