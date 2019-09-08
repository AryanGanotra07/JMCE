package com.aryanganotra.jmceconomics.notes.ui

import com.aryanganotra.jmceconomics.notes.model.Tab

interface NoteClickListener {

    fun onNoteClick(note : Tab.Course.Note)

}