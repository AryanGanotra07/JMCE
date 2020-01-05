package com.aryanganotra.jmceconomics.notes.ui

import com.aryanganotra.jmceconomics.notes.model.Note


interface NoteClickListener {

    fun onNoteClick(note : Note)

}