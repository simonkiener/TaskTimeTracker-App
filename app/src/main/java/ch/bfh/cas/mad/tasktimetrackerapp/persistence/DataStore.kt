package ch.bfh.cas.mad.tasktimetrackerapp.persistence

object DataStore {
    val projects = mutableListOf(
        Project(1,"Project 1"),
        Project(2, "Project 2"),
        Project(3, "Project 3"),
        Project(4, "Project 4"),
        Project(5, "Project 5"),
        Project(6, "Project 6")
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
        Entry(1,  taskId = 1,  description = "Entry 1",  timeStamp = 1708412400000),
        Entry(2,  taskId = 1,  description = "Entry 2",  timeStamp = 1708417800000),
        Entry(3,  taskId = 2,  description = "Entry 3",  timeStamp = 1708417800000),
        Entry(4,  taskId = 3,  description = "Entry 4",  timeStamp = 1708418100000),
        Entry(5,  taskId = 3,  description = "Entry 5",  timeStamp = 1708427125000),
        Entry(6,  taskId = 3,  description = "Entry 6",  timeStamp = 1708435125000),
        Entry(7,  taskId = 4,  description = "Entry 7",  timeStamp = 1708438521000),
        Entry(8,  taskId = 4,  description = "Entry 8",  timeStamp = 1708438523000),
        Entry(9,  taskId = 5,  description = "Entry 9",  timeStamp = 1708439999000),
        Entry(10, taskId = 5,  description = "Entry 10", timeStamp = 1708445678000),
        Entry(11, taskId = 5,  description = "Entry 11", timeStamp = 1708452328000),
        Entry(12, taskId = 6,  description = "Entry 12", timeStamp = 1708725577253),  // entspricht 23.02.2024 aus Datepicker
        Entry(13, taskId = 6,  description = "Entry 13", timeStamp = 1708811977253),  // entspricht 24.02.2024 aus Datepicker
        Entry(14, taskId = 6,  description = "Entry 14", timeStamp = 1234567890),
        Entry(15, taskId = 6,  description = "Entry 15", timeStamp = 1234567890),
        Entry(16, taskId = 7,  description = "Entry 16", timeStamp = 1234567890),
        Entry(17, taskId = 7,  description = "Entry 17", timeStamp = 1234567890),
        Entry(18, taskId = 7,  description = "Entry 18", timeStamp = 1234567890),
        Entry(19, taskId = 8,  description = "Entry 19", timeStamp = 1234567890),
        Entry(20, taskId = 8,  description = "Entry 20", timeStamp = 1234567890),
        Entry(21, taskId = 8,  description = "Entry 21", timeStamp = 1234567890),
        Entry(22, taskId = 9,  description = "Entry 22", timeStamp = 1234567890),
        Entry(23, taskId = 9,  description = "Entry 23", timeStamp = 1234567890),
        Entry(24, taskId = 9,  description = "Entry 24", timeStamp = 1234567890),
        Entry(25, taskId = 10, description = "Entry 25", timeStamp = 1234567890),
        Entry(26, taskId = 10, description = "Entry 26", timeStamp = 1234567890),
        Entry(27, taskId = 11, description = "Entry 27", timeStamp = 1234567890),
        Entry(28, taskId = 11, description = "Entry 28", timeStamp = 1234567890),
        Entry(29, taskId = 11, description = "Entry 29", timeStamp = 1234567890),
        Entry(30, taskId = 12, description = "Entry 30", timeStamp = 1234567890),
        Entry(31, taskId = 12, description = "Entry 31", timeStamp = 1234567890),
        Entry(32, taskId = 13, description = "Entry 32", timeStamp = 1234567890),
        Entry(33, taskId = 13, description = "Entry 33", timeStamp = 1234567890),
        Entry(34, taskId = 13, description = "Entry 34", timeStamp = 1234567890),
        Entry(35, taskId = 14, description = "Entry 35", timeStamp = 1234567890),
        Entry(36, taskId = 14, description = "Entry 36", timeStamp = 1234567890),
        Entry(37, taskId = 15, description = "Entry 37", timeStamp = 1234567890),
        Entry(38, taskId = 15, description = "Entry 38", timeStamp = 1234567890),
        Entry(39, taskId = 15, description = "Entry 39", timeStamp = 1234567890),
        Entry(40, taskId = 16, description = "Entry 40", timeStamp = 1234567890),
        Entry(41, taskId = 16, description = "Entry 41", timeStamp = 1234567890)
    )

    val widgetTasks = mutableListOf(
        WidgetTask(1, 0),
        WidgetTask(2, 0),
        WidgetTask(3, 0),
        WidgetTask(4, 0)
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
        return projects.find { it.id == task?.projectId } ?: Project(0, "")
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