package com.viplearner.common.data.local

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.viplearner.common.data.local.dao.NotesDao
import com.viplearner.common.data.local.database.NotesDatabase
import com.viplearner.common.data.local.dto.Note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class NoteDaoTest {
    private lateinit var notesDatabase: NotesDatabase
    private lateinit var noteDao: NotesDao

    @Before
    fun setup() {
        notesDatabase = Room.inMemoryDatabaseBuilder(
            context = ApplicationProvider.getApplicationContext(),
            klass = NotesDatabase::class.java
        ).allowMainThreadQueries().build()

        noteDao = notesDatabase.notesDao()
    }

    @After
    fun tearDown() {
        notesDatabase.close()
    }

    @Test
    fun insertItem() {
        runTest {
            val note = Note(title = "How to make pancakes", content = "Whisk egg" )
            noteDao.upsert(note)

            val favouriteList = noteDao.getAll()
            assert(favouriteList.contains(note))
        }
    }

    @Test
    fun deleteItem() {
        runTest {
            val note = Note(title = "How to make pancakes", content = "Whisk egg" )
            noteDao.upsert(note)
            noteDao.delete(note.uuid)

            val favouriteList = noteDao.getAll()
            assert(favouriteList.contains(note).not())
        }
    }
}