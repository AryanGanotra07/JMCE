package com.aryanganotra.jmceconomics.notes.ui.noteslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryanganotra.jmceconomics.notes.model.Tab

class NoteListViewModel : ViewModel() {

    private val noteName : MutableLiveData<String> = MutableLiveData()

    fun bind (note : Tab.Course.Note){
        noteName.value = note.noteName
    }

    fun getNoteName() : MutableLiveData<String>{
        return noteName
    }
}