package com.syntxr.noteas.todo

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.syntxr.noteas.R
import com.syntxr.noteas.data.notes.NotesDatabases
import com.syntxr.noteas.ui.TodoAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class ToDoListFragment : Fragment(R.layout.fragment_to_do_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_todo)
        val fab = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_todo)
        val database = NotesDatabases.getInstance(requireContext())

        fab.setOnClickListener {
            findNavController().navigate(R.id.action_spapersFragment_to_insertTodoFragment)
        }

        lifecycleScope.launch {
            val todoList = database.TodoDao().getTodo()
            todoList.collect {
                val adapter = TodoAdapter(it)
                recyclerView.adapter = adapter

                adapter.itemClickListener = {
                    val bundle = bundleOf("id" to it.id)
                    findNavController().navigate(R.id.action_spapersFragment_to_todoDetailFragment,bundle)

                }

                adapter.itemCheckListener = {
                    lifecycleScope.launch {
                        database.TodoDao().updateTodo(it)
                    }
                }

                adapter.deleteItemClickListener = {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Delete")
                        .setMessage("are you sure want to delete this todo")
                        .setPositiveButton("delete") { dialog, which ->
                            lifecycleScope.launch {
                                database.TodoDao().deleteTodo(it)
                                Toast.makeText(
                                    requireContext(),
                                    "todo was deleted",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        .setNegativeButton("cancel") { dialog, which ->
                            dialog.dismiss()
                        }.show()
                }

                adapter.copyItemClickListener = {
                    val itemCopy = "${it.title} \n ${it.desc}"
                    val clipboard =
                        requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("copy todo", itemCopy)
                    clipboard.setPrimaryClip(clipData)

                    Toast.makeText(requireContext(), "Todo telah disalin ", Toast.LENGTH_SHORT)
                        .show()
                }

            }

        }
    }

}