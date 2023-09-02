package com.viplearner.common.data.local.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "Notes")
data class Note(
    @PrimaryKey @ColumnInfo(name = "uuid") val uuid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "timeLastEdited") val timeLastEdited: Long,
    @ColumnInfo(name = "isPinned", defaultValue = "0") var isPinned: Boolean
)
