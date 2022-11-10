package com.syntxr.noteas.data.notes

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.syntxr.noteas.data.todo.Todo
import com.syntxr.noteas.data.todo.TodoDao


@Database(version = 2, entities = [Notes::class, Todo::class])
abstract class NotesDatabases : RoomDatabase() {

    abstract fun NotesDao(): NotesDao
    abstract fun TodoDao() : TodoDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabases? = null

        fun getInstance(context: Context): NotesDatabases {
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    NotesDatabases::class.java,
                    "notes.db"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}