package com.aryanganotra.jmceconomics.notes.ui.noteslist

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.databinding.ItemNoteBinding
import com.aryanganotra.jmceconomics.notes.model.Tab
import com.aryanganotra.jmceconomics.notes.ui.NoteClickListener

class NotesListAdapter(private val callback : NoteClickListener) : RecyclerView.Adapter<NotesListAdapter.ViewHolder>() {

    private lateinit var notes : List<Tab.Course.Note>

    fun setNotesList (notes : List<Tab.Course.Note>){
        this.notes = notes
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
     val binding : ItemNoteBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_note , parent, false)
        return ViewHolder(binding,callback)
    }

    override fun getItemCount(): Int {
        if (::notes.isInitialized){
            return notes.size
        }
        else{
            return 0
        }

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
holder.bind(notes[position])
    }


    class ViewHolder(private val binding : ItemNoteBinding ,private val callback: NoteClickListener) : RecyclerView.ViewHolder(binding.root){
        val viewModel : NoteListViewModel = NoteListViewModel()
 fun bind(note : Tab.Course.Note){
     viewModel.bind(note)
     binding.viewModel = viewModel

     itemView.setOnClickListener {
        callback.onNoteClick(note)

     }
 }

    }
}