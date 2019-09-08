package com.aryanganotra.jmceconomics.notes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aryanganotra.jmceconomics.notes.model.Tab

class NotesViewModel : ViewModel() {

    private val year : MutableLiveData<String> = MutableLiveData()
    private val courses : MutableLiveData<List<Tab.Course>> = MutableLiveData()
    private val notes : MutableLiveData<List<Tab.Course.Note>> = MutableLiveData()
    private val courseName : MutableLiveData<String> = MutableLiveData()

    fun bind (course: Tab.Course){
       courseName.value = course.courseName
    }

    fun getYear() : LiveData<String>{
        return year
    }

    fun getCourses() : LiveData<List<Tab.Course>> {
        return courses
    }

    fun getCourseName() : MutableLiveData<String>{
        return courseName
    }





}