package com.viplearner.common.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.viplearner.common.data.local.dao.NotesDao
import com.viplearner.common.data.local.dto.Note

@Database(
    entities = [Note::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
        AutoMigration(from = 2, to = 3)
    ]
)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}