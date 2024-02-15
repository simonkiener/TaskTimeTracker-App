package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Project::class, Task::class, Entry::class], version = 1)
abstract class TTTDatabase: RoomDatabase() {
    abstract fun getProjectDao(): ProjectDao

    abstract fun getTaskDao(): TaskDao

    abstract fun getEntryDao(): EntryDao
}