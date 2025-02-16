package com.robotbot.avito.data.music.sources.local.base

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.robotbot.avito.common.SongAlreadyExistsException
import com.robotbot.avito.common.StorageException

inline fun <T> wrapSQLiteException(block: () -> T): T {
    return try {
        block()
    } catch (e: SQLiteConstraintException) {
        throw SongAlreadyExistsException(e)
    }
    catch (e: SQLiteException) {
        throw StorageException(e)
    }
}