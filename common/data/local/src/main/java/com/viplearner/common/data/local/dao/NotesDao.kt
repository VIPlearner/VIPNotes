package com.viplearner.common.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.viplearner.common.data.local.dto.Note
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {
    @Query("SELECT * from Notes" +
            " ORDER BY timeLastEdited DESC")
    fun getAllFlow(): Flow<List<Note>>

    @Query("SELECT * from Notes" +
            " ORDER BY timeLastEdited DESC")
    fun getAll(): List<Note>

    @Query("SELECT * " +
            "FROM Notes " +
            "WHERE (title LIKE '%' || :searchText || '%' " +
            "OR content LIKE '%' || :searchText || '%')" +
            "OR :searchText = ''" +
            "ORDER BY timeLastEdited DESC")
    fun getNotesBySearchText(searchText: String): Flow<List<Note>>

    @Query("SELECT * " +
            "FROM Notes " +
            "WHERE uuid=:uuid")
    fun getNoteUsingUUID(uuid: String): Note

    @Query("SELECT EXISTS(SELECT * FROM Notes WHERE :uuid = uuid)")
    fun noteExists(uuid: String): Boolean

    @Upsert
    fun upsert(note: Note)

    @Query("DELETE from Notes where uuid = :uuid")
    fun delete(uuid: String)
}