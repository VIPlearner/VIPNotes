package com.viplearner.common.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.viplearner.common.data.local.dto.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * from Notes")
    fun getAll(): Flow<List<Note>>

    @Upsert
    fun upsert(note: Note)

    @Query("DELETE from Notes where uuid = :uuid")
    fun delete(uuid: String)
}