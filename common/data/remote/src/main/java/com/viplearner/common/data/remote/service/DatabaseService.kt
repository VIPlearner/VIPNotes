package com.viplearner.common.data.remote.service

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.viplearner.common.data.remote.di.IoDispatcher
import com.viplearner.common.data.remote.dto.Note
import com.viplearner.common.domain.datastore.NotesDataStoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class DatabaseService @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val notesDataStoreRepository: NotesDataStoreRepository
){

    companion object {
        val dbInstance = Firebase.database
    }

    private fun attachValueListenerToTaskCompletion(src: TaskCompletionSource<DataSnapshot>): ValueEventListener {
        return (object : ValueEventListener {
            override fun onCancelled(error: DatabaseError) { src.setException(Exception(error.message)) }

            override fun onDataChange(snapshot: DataSnapshot) { src.setResult(snapshot) }
        })
    }

    //region Private

    private fun refToPath(path: String): DatabaseReference {
        return dbInstance.reference.child(path)
    }

    fun loadNote(uid: String, noteUUID: String): Task<DataSnapshot>{
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users/$uid/notes/$noteUUID").addListenerForSingleValueEvent(listener)
        return src.task
    }

    fun loadAllNotesTask(uid: String): Task<DataSnapshot> {
        val src = TaskCompletionSource<DataSnapshot>()
        val listener = attachValueListenerToTaskCompletion(src)
        refToPath("users/$uid/notes").addListenerForSingleValueEvent(listener)
        return src.task
    }

    fun updateNote(uid: String, note: Note) {
        refToPath("users/$uid/notes/${note.uuid}").setValue(note)
    }

    fun updateNotes(uid: String, notes: List<Note>) {
        notes.forEach { note ->
            updateNote(uid, note)
        }
    }

    fun deleteNote(
        uid: String,
        noteUUID: String,
    ) {
        refToPath("users/$uid/notes/$noteUUID").removeValue()
    }
    fun deleteNotes(uid: String, notes: List<Note>) {
        notes.forEach { note ->
            deleteNote(uid, note.uuid)
        }
    }
}