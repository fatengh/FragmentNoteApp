package com.example.fragmentnoteapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fragmentnoteapp.databinding.ItemRowBinding

class MyAdap(private val listFragment: Fragment_list_note): RecyclerView.Adapter<MyAdap.ItemViewHolder>() {
    private var notes = emptyList<Note>()

    class ItemViewHolder(val binding: ItemRowBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdap.ItemViewHolder {
        return ItemViewHolder(
            ItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val note = notes[position]

        holder.binding.apply {
            tv.text = note.note
            imEd.setOnClickListener {
                with(listFragment.sp.edit()) {
                    putString("NoteId", note.id)
                    apply()
                }
                listFragment.findNavController().navigate(R.id.action_list_to_update)
            }
            imDel.setOnClickListener {
                listFragment.listVM.delNote(note.id)
            }
        }
    }

    override fun getItemCount() = notes.size

    fun updaterv(notes: List<Note>){
        this.notes = notes
        notifyDataSetChanged()
    }
}