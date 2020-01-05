package com.aryanganotra.jmceconomics.notes.ui

import android.app.Activity
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.databinding.NotesFragmentBinding
import com.aryanganotra.jmceconomics.notes.ui.noteslist.NotesViewModelFactory
import com.aryanganotra.jmcemanager.model.Course
import kotlinx.android.synthetic.main.notes_fragment.*


class NotesFragment() : Fragment() {
    private lateinit var binding : NotesFragmentBinding
    private lateinit var notesListViewModel: NotesListViewModel
    private val adapter : SubjectListAdapter = SubjectListAdapter()
     internal lateinit var callback : CourseClickListener


    fun setCourseClickListener (callback : CourseClickListener){
        this.callback=callback
       adapter.setCallback(callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding = DataBindingUtil.inflate(inflater,R.layout.notes_fragment,container,false)

        binding.viewModel= this
        binding.recyclerView.layoutManager = LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        retainInstance = true
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        notesListViewModel = ViewModelProviders.of(this,
            NotesViewModelFactory(activity!!.application,context as AppCompatActivity)
        ).get(NotesListViewModel:: class.java)
        val tab = arguments!!.get("tab") as Int
        notesListViewModel.getCoursesLiveData().observe(this, Observer {
            progress_circular.visibility = View.GONE
            if (it!=null)
            {
                val courses = it.filter { course -> course.year == tab+1 }
                if (courses.isNullOrEmpty())
                {
                    adapter.setCourses(ArrayList())
                }
                else
                {
                    adapter.setCourses(courses as ArrayList<Course>)
                }
            }
            else
            {
                adapter.setCourses(ArrayList())
            }
        })


    }





    interface CourseClickListener  {
        fun onCourseClicked(course : Course)
    }

    fun getCourseListAdapter() : SubjectListAdapter{
        return  adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

       // val tab = arguments!!.get("tab") as Tab
       // if (isAdded)
       // adapter.setTab(tab)


    }

    override fun onResume() {
        super.onResume()

      //  val tab = arguments!!.get("tab") as Tab
      //  if (isAdded)
       // adapter.setTab(tab)


    }

    override fun onPause() {
        super.onPause()

      //  Log.i("Pause","Yes")
    }


}