package ch.bfh.cas.mad.tasktimetrackerapp

import java.util.UUID

data class Task(
    val id: Int,
    val name: String,
    val projectId: Int){
}
