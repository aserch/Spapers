package com.syntxr.noteas.note

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.syntxr.noteas.R
import com.syntxr.noteas.data.notes.Notes
import com.syntxr.noteas.data.notes.NotesDatabases
import com.syntxr.noteas.ui.NotesAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class NoteListFragment : Fragment(R.layout.fragment_note_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rv_list)
        val fab = view.findViewById<ExtendedFloatingActionButton>(R.id.fab_note)
        val database = NotesDatabases.getInstance(requireContext())




        lifecycleScope.launch {
            val noteList: Flow<List<Notes>> = database.NotesDao().getNotesList()
            noteList.collect {
                val adapter = NotesAdapter(it)
                recyclerView.adapter = adapter
                adapter.itemClickListener = {
                    lifecycleScope.launch {
                        database.NotesDao().updateNotes(it)
                        val  bundle = bundleOf("id" to it.id)
                        findNavController().navigate(R.id.action_spapersFragment_to_noteDetailFragment,bundle)
                    }
                }

                adapter.deleteClickListener = {
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Delete")
                        .setMessage("are you sure want to delete this note ?")
                        .setPositiveButton("Yes") { dialog, which ->
                            lifecycleScope.launch {
                                database.NotesDao().deleteNotes(it)
                                Toast.makeText(requireContext(), "note was delete", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .setNegativeButton("No"){dialog,which ->
                            dialog.dismiss()
                        }.show()
                }

                fab.setOnClickListener {
                    findNavController().navigate(R.id.action_spapersFragment_to_inputNoteFragment)
                }
            }
        }
    }
}