package com.syntxr.noteas.note

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.syntxr.noteas.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.syntxr.noteas.data.notes.Notes
import com.syntxr.noteas.data.notes.NotesDatabases
import kotlinx.coroutines.launch

class NoteDetailFragment : Fragment(R.layout.fragment_note_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar =
            view.findViewById<AppBarLayout>(R.id.app_bar_update).findViewById<MaterialToolbar>(R.id.material_toolbar)
        val updateTitle = view.findViewById<EditText>(R.id.update_title)
        val updateNote = view.findViewById<EditText>(R.id.update_note)
//        val btnUpdate = findViewById<ImageButton>(R.id.btn_save_change)
        val getId = arguments?.getInt("id", 0)?:0
        val database = NotesDatabases.getInstance(requireContext())

        lifecycleScope.launch {
            val note = database.NotesDao().getNoteDetail(noteId = getId)
            updateTitle.setText(note.title)
            updateNote.setText(note.note)
        }



        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.itm_save -> {
                    val title = updateTitle.text.toString()
                    val note = updateNote.text.toString()

                    lifecycleScope.launch {
                        database.NotesDao().updateNotes(
                            Notes(
                                id = getId,
                                title = title,
                                note = note,
                            )
                        )
                        Toast.makeText(requireContext(), "note was updated", Toast.LENGTH_SHORT)
                            .show()
                        findNavController().navigate(R.id.action_noteDetailFragment_to_spapersFragment)

                    }
                    true
                }
                R.id.itm_share -> {
                    val title = updateTitle.text.toString()
                    val note = updateNote.text.toString()
                    ShareCompat.IntentBuilder(requireContext())
                        .setText("$title \n $note")
                        .setChooserTitle("bagikan dengan")
                        .setType("text/plain")
                        .startChooser()
                    true
                }
                R.id.itm_copy -> {
                    val title = updateTitle.text.toString()
                    val note = updateNote.text.toString()
                    val itemCopy = "$title \n $note"
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



        updateTitle.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK){
                updateTitle.clearFocus()
                true
            }else{
                false
            }
        }

        updateNote.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK){
                updateNote.clearFocus()
                true
            }else{
                false
            }
        }
    }



//    override fun onNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
}