package com.robotbot.avito.data.music.sources.local.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.robotbot.avito.data.music.sources.local.model.SongDbModel

@Database(
    entities = [
        SongDbModel::class
    ],
    version = 1,
    exportSchema = false
)
abstract class SongDatabase : RoomDatabase() {

    companion object {
        private var db: SongDatabase? = null
        private const val DB_NAME = "songs.db"
        private val LOCK = Any()

        fun getInstance(context: Context): SongDatabase {
            db?.let { return it }
            synchronized(LOCK) {
                db?.let { return it }
                val instance =
                    Room.databaseBuilder(
                        context = context,
                        klass = SongDatabase::class.java,
                        name = DB_NAME
                    ).build()
                return instance.also {
                    db = it
                }
            }
        }
    }

    abstract fun localSongsDao(): LocalSongsDao

}