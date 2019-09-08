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
import com.aryanganotra.jmceconomics.notes.model.Tab

class SubjectListAdapter() : RecyclerView.Adapter<SubjectListAdapter.ViewHolder>() {

    private lateinit var tab : Tab
    private lateinit var callback : NotesFragment.CourseClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
         val binding : ItemCourseBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_course , parent, false)
        return ViewHolder(binding,callback)
    }

    fun setCallback (callback : NotesFragment.CourseClickListener){
        this.callback = callback

    }

    override fun getItemCount(): Int {
       if (::tab.isInitialized){
           return tab.courses.size
       }
        else{
           return 0
       }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bind(tab.courses.get(position))

    }

    fun setTab(tab : Tab){
        this.tab = tab
        notifyDataSetChanged()
    }



    class ViewHolder(private val binding: ItemCourseBinding, private val callback: NotesFragment.CourseClickListener) : RecyclerView.ViewHolder(binding.root) {
        val viewModel : NotesViewModel = NotesViewModel()
               fun bind(course : Tab.Course){
                   viewModel.bind(course)
                   binding.viewModel = viewModel

                   itemView.setOnClickListener {
                       if (callback != null )
                       callback.onCourseClicked(course)
                   }


               }


    }
}