package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import android.content.Context
import androidx.room.Room

object TTTDatabaseProvider {
    fun get(context: Context): TTTDatabase {
        return Room.databaseBuilder(
            context = context,
            klass = TTTDatabase::class.java,
            name = "TTT.db"
        ).build()
    }
}