package com.example.fragmentnoteapp
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*



class Fragment_list_note : Fragment() {

    private lateinit var rv: RecyclerView
    private lateinit var myAdap: MyAdap
    private lateinit var editText: EditText
    private lateinit var submitBtn: Button
    lateinit var listVM: ListNoteVM
    private lateinit var notes: List<Note>
    lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_note, container, false)

        sp = requireActivity().getSharedPreferences(
            getString(R.string.preference_file_key), Context.MODE_PRIVATE)

        notes = arrayListOf()

        listVM = ViewModelProvider(this).get(ListNoteVM::class.java)
        listVM.getNotes().observe(viewLifecycleOwner, {
                notes -> myAdap.updaterv(notes)
        })

        editText = view.findViewById(R.id.tvNote)
        submitBtn = view.findViewById(R.id.btnSub)
        submitBtn.setOnClickListener {
            listVM.addNote(Note("", editText.text.toString()))
            editText.text.clear()
            editText.clearFocus()
        }

        rv = view.findViewById(R.id.rv)
        myAdap = MyAdap(this)
        rv.adapter = myAdap
        rv.layoutManager = LinearLayoutManager(requireContext())

        listVM.getNote()

        return view
    }

    override fun onResume() {
        super.onResume()
        CoroutineScope(Dispatchers.IO).launch {
            delay(1000)
            listVM.getNote()
        }
    }
}