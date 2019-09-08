package com.aryanganotra.jmceconomics.notes.ui.noteslist

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.aryanganotra.jmceconomics.articles.injection.ViewModelFactory
import com.aryanganotra.jmceconomics.notes.ui.NotesListViewModel

class NotesViewModelFactory(private val application: Application , private val activity : AppCompatActivity) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotesListViewModel(application , activity.supportFragmentManager) as T
    }


}