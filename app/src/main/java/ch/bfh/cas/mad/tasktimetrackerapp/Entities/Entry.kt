package ch.bfh.cas.mad.tasktimetrackerapp.Entities

data class Entry(
    val description: String,
    val taskId: Int,
    val duration: Int)