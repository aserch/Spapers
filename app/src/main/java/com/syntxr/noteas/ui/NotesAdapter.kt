package com.syntxr.noteas.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.syntxr.noteas.R
import com.syntxr.noteas.data.notes.Notes

class NotesAdapter(val noteList : List<Notes>) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    var itemClickListener : ((Notes) -> Unit)? = null
    var deleteClickListener : ((Notes) -> Unit)? = null

    class NotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val textNote = itemView.findViewById<TextView>(R.id.tv_note)
        val btnDelete = itemView.findViewById<ImageButton>(R.id.btn_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notes,parent,false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val notes = noteList[position]
        holder.textNote.text = notes.note
        holder.textTitle.text = notes.title
        holder.itemView.setOnClickListener {
            itemClickListener?.invoke(notes)
        }
        holder.btnDelete.setOnClickListener {
            deleteClickListener?.invoke(notes)
        }

    }

    override fun getItemCount(): Int {
        return noteList.size
    }
}