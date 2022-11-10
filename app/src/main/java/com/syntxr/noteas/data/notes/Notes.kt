package com.syntxr.noteas.data.notes

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class Notes(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    val title : String = "",
    val note : String = "",
)
