package ch.bfh.cas.mad.tasktimetrackerapp

data class Entry(
    val description: String,
    val taskId: Int,
    val duration: Int)