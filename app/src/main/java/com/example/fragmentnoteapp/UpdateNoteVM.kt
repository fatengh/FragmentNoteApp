package com.example.fragmentnoteapp

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
class UpdateNoteVM (application: Application): AndroidViewModel(application) {
    private var db: FirebaseFirestore = Firebase.firestore

    fun editNote(noteID: String, noteText: String){
        CoroutineScope(Dispatchers.IO).launch {
            db.collection("notes")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if(document.id == noteID){
                            db.collection("notes").document(noteID).update("note", noteText)
                            break
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w("MainActivity", "cant get data", exception)
                }
        }
    }
}