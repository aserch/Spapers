package com.syntxr.noteas.data.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    var title : String,
    var desc : String,
    @ColumnInfo(name = "is_Checked")
    var isChecked : Boolean

)
