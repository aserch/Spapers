package com.syntxr.noteas.note

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syntxr.noteas.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.syntxr.noteas.data.notes.Notes
import com.syntxr.noteas.data.notes.NotesDatabases
import kotlinx.coroutines.launch

class InputNoteFragment : Fragment(R.layout.fragment_input_note) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val inputTitle = view.findViewById<EditText>(R.id.input_title)
        val inputNote = view.findViewById<EditText>(R.id.input_note)
//        val btnInput = findViewById<ImageButton>(R.id.btn_save_input)
        val toolbar =
            view.findViewById<AppBarLayout>(R.id.app_bar_input).findViewById<MaterialToolbar>(R.id.material_toolbar)
        val database = NotesDatabases.getInstance(requireContext())

        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itm_save -> {
                    val title = inputTitle.text.toString()
                    val note = inputNote.text.toString()
                    val notes = Notes(
                        title = title,
                        note = note,
                    )

                    if (note.isEmpty() && title.isEmpty()) {
                        inputTitle.setError("title is empty")
                        inputNote.setError("note is empty")
                    } else {
                        lifecycleScope.launch {
                            database.NotesDao().insertNotes(notes)
                            Toast.makeText(
                                requireContext(),
                                "notes was input",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            findNavController().navigate(R.id.action_inputNoteFragment_to_spapersFragment)

                        }
                    }


                    true
                }
                else -> false
            }
        }

//
//        inputTitle.addTextChangedListener(afterTextChanged = {
//            inputTitle.clearFocus()
//        })

        inputTitle.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                inputTitle.clearFocus()
                true
            } else {
                false
            }
        }



        inputNote.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                inputNote.clearFocus()
                true
            } else {
                false
            }
        }
    }

//    override fun onNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}