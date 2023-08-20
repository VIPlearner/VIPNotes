package com.viplearner.common.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.viplearner.common.data.local.dao.NotesDao
import com.viplearner.common.data.local.dto.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
}