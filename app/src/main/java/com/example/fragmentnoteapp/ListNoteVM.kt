package com.example.fragmentnoteapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class ListNoteVM(application: Application) : AndroidViewModel(application) {
    private val notes: MutableLiveData<List<Note>> = MutableLiveData()
    private var db: FirebaseFirestore = Firebase.firestore

    fun getNotes(): LiveData<List<Note>> {
        return notes
    }

    fun getNote() {
        db.collection("notes")
            .get()
            .addOnSuccessListener { result ->
                val temp = arrayListOf<Note>()
                for (document in result) {
                    document.data.map { (key, value) ->
                        temp.add(
                            Note(
                                document.id,
                                value.toString()
                            )
                        )
                    }
                }
                notes.postValue(temp)
            }
            .addOnFailureListener { exception ->
                Log.w("MainActivity", "cant get data", exception)
            }
    }

    fun addNote(note: Note) {
        CoroutineScope(Dispatchers.IO).launch {
            val newNote = hashMapOf(
                "noteText" to note.note,
            )
            db.collection("notes").add(newNote)
            getNote()
        }
    }

    fun delNote(noteID: String) {
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.id == noteID) {
                            db.collection("notes").document(noteID).delete()
                        }
                    }
                    getNote()
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "cant get data", exception)
                }
        }
    }
}