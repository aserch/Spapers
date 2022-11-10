package com.syntxr.noteas.data.notes

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NotesDao {

    @Query("SELECT * FROM notes")
    fun getNotesList() : Flow<List<Notes>>

    @Insert
    suspend fun insertNotes(notes: Notes)

    @Delete
    suspend fun deleteNotes(notes: Notes)

    @Update
    suspend fun updateNotes(notes: Notes)

    @Query("SELECT * FROM notes WHERE id = :noteId")
    suspend fun getNoteDetail(noteId : Int) : Notes
}