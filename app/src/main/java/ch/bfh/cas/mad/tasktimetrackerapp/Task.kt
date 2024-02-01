package ch.bfh.cas.mad.tasktimetrackerapp

import java.util.UUID

data class Task(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val projectId: Int){
}
