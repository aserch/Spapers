package com.syntxr.noteas.todo

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syntxr.noteas.R
import com.syntxr.noteas.data.notes.NotesDatabases
import com.syntxr.noteas.data.todo.Todo
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class InsertTodoFragment : Fragment(R.layout.fragment_insert_todo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<AppBarLayout>(R.id.app_bar_insert).findViewById<MaterialToolbar>(
            R.id.material_toolbar
        )
        val insertTitle = view.findViewById<EditText>(R.id.insert_title)
        val insertNote = view.findViewById<EditText>(R.id.insert_note)
        val database = NotesDatabases.getInstance(requireContext())

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.itm_save ->{
                    val title = insertTitle.text.toString()
                    val desc = insertNote.text.toString()
                    val todo = Todo(
                        title = title,
                        desc = desc,
                        isChecked = false
                    )
                    lifecycleScope.launch {
                        database.TodoDao().insertTodo(todo)
                        Toast.makeText(requireContext(), "todo list was insert", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_insertTodoFragment_to_spapersFragment)
                    }
                    true
                }
                else -> false
            }
        }


    }
}