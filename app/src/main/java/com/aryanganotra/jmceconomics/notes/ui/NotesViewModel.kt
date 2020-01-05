package com.aryanganotra.jmceconomics.notes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryanganotra.jmceconomics.notes.model.Note
import com.aryanganotra.jmcemanager.model.Course

class NotesViewModel : ViewModel() {

    private val year : MutableLiveData<String> = MutableLiveData()
    private val courses : MutableLiveData<List<Course>> = MutableLiveData()
    private val notes : MutableLiveData<List<Note>> = MutableLiveData()
    private val courseName : MutableLiveData<String> = MutableLiveData()

    fun bind (course: Course){
       courseName.value = course.courseName
    }

    fun getYear() : LiveData<String>{
        return year
    }

    fun getCourses() : LiveData<List<Course>> {
        return courses
    }

    fun getCourseName() : MutableLiveData<String>{
        return courseName
    }





}