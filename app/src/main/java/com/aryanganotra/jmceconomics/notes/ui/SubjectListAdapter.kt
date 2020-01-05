package com.aryanganotra.jmceconomics.notes.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.databinding.ItemCourseBinding
import com.aryanganotra.jmcemanager.model.Course

class SubjectListAdapter() : RecyclerView.Adapter<SubjectListAdapter.ViewHolder>() {

    private lateinit var courses : ArrayList<Course>
    private lateinit var callback : NotesFragment.CourseClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val binding : ItemCourseBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_course , parent, false)
        return ViewHolder(binding,callback)
    }

    fun setCallback (callback : NotesFragment.CourseClickListener){
        this.callback = callback

    }

    override fun getItemCount(): Int {
       if (::courses.isInitialized){
           return courses.size
       }
        else{
           return 0
       }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(courses.get(position))

    }

    fun setCourses(courses : ArrayList<Course>){
        this.courses = courses
        notifyDataSetChanged()
    }



    class ViewHolder(private val binding: ItemCourseBinding, private val callback: NotesFragment.CourseClickListener) : RecyclerView.ViewHolder(binding.root) {
        val viewModel : NotesViewModel = NotesViewModel()
               fun bind(course : Course){
                   viewModel.bind(course)
                   binding.viewModel = viewModel

                   itemView.setOnClickListener {
                       if (callback != null )
                       callback.onCourseClicked(course)
                   }


               }


    }
}