package com.example.fragmentnoteapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

class Fragment_update_note : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update_note, container, false)

        val sharedPreferences = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        val updateViewModel = ViewModelProvider(this).get(UpdateNoteVM::class.java)

        val etUpdate = view.findViewById<EditText>(R.id.etUpdate)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            val noteId = sharedPreferences.getString("NoteId", "").toString()
            updateViewModel.editNote(noteId, etUpdate.text.toString())
            with(sharedPreferences.edit()) {
                putString("NoteId", etUpdate.text.toString())
                apply()
            }
            findNavController().navigate(R.id.action_update_to_list)
        }

        return view
    }
}