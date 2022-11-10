package com.syntxr.noteas.ui

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syntxr.noteas.R
import com.syntxr.noteas.data.todo.Todo
import com.google.android.material.checkbox.MaterialCheckBox

class TodoAdapter (val todoList : List<Todo>) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    var itemCheckListener : ((Todo) -> Unit)? = null
    var itemClickListener : ((Todo) -> Unit)? = null
    var deleteItemClickListener : ((Todo) -> Unit)? = null
    var copyItemClickListener : ((Todo) -> Unit)? = null

    class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val CheckBox = itemView.findViewById<MaterialCheckBox>(R.id.checkbox_todo)
        val btn_delete = itemView.findViewById<ImageButton>(R.id.btn_delete_todo)
        val txt_todo = itemView.findViewById<TextView>(R.id.txt_todo)
        val copy_delete = itemView.findViewById<ImageButton>(R.id.btn_copy_todo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_list,parent,false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = todoList[position]
        holder.CheckBox.isChecked = todo.isChecked
        holder.txt_todo.text = todo.title
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(todo)
        }
        if (todo.isChecked){
            holder.txt_todo.paintFlags =
            holder.txt_todo.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }else{
            holder.txt_todo.paintFlags =
            holder.txt_todo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.CheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            itemCheckListener?.invoke(todo.copy(isChecked = isChecked))
            if (isChecked){
                holder.txt_todo.paintFlags =
                holder.txt_todo.paintFlags or (Paint.STRIKE_THRU_TEXT_FLAG)
            }else{
                holder.txt_todo.paintFlags =
                holder.txt_todo.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            }
        }

        holder.btn_delete.setOnClickListener {
            deleteItemClickListener?.invoke(todo)
        }

        holder.copy_delete.setOnClickListener {
            copyItemClickListener?.invoke(todo)
        }
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

}