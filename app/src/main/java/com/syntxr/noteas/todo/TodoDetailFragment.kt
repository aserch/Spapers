package com.syntxr.noteas.todo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syntxr.noteas.R
import com.syntxr.noteas.data.notes.NotesDatabases
import com.syntxr.noteas.data.todo.Todo
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class TodoDetailFragment : Fragment(R.layout.fragment_todo_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<AppBarLayout>(R.id.app_bar_renew).findViewById<MaterialToolbar>(R.id.material_toolbar)
        val renewTitle = view.findViewById<EditText>(R.id.renew_title)
        val renewDesc = view.findViewById<EditText>(R.id.renew_desc)
        val getId = arguments?.getInt("id",0) ?: 0

        val database = NotesDatabases.getInstance(requireContext())

        lifecycleScope.launch {
            val todo = database.TodoDao().getTodoDetail(todoId = getId)
            renewTitle.setText(todo.title)
            renewDesc.setText(todo.desc)
            val ischeck = todo.isChecked
        }

        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.itm_save -> {
                    val title = renewTitle.text.toString()
                    val desc = renewDesc.text.toString()

                    lifecycleScope.launch{
                        database.TodoDao().updateTodo(
                            Todo(
                                title = title,
                                desc = desc,
                                id = getId,
                                isChecked = it.isChecked
                            )
                        )
                        Toast.makeText(requireContext(), "todo list was updated", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_todoDetailFragment_to_spapersFragment)
                    }
                    true
                }
                R.id.itm_share -> {
                    val title = renewTitle.text.toString()
                    val desc = renewDesc.text.toString()
                    ShareCompat.IntentBuilder(requireContext())
                        .setType("text/plain")
                        .setText("$title \n $desc")
                        .setChooserTitle("bagikan dengan")
                        .startChooser()
                    true
                }
                R.id.itm_copy -> {
                    val title = renewTitle.text.toString()
                    val desc = renewDesc.text.toString()
                    val itemCopy = "$title \n $desc"
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("copy todo", itemCopy)
                    clipboard.setPrimaryClip(clipData)

                    Toast.makeText(requireContext(), "Todo telah disalin ", Toast.LENGTH_SHORT)
                        .show()
                    true
                }
                else -> false
            }
        }
        
    }
}