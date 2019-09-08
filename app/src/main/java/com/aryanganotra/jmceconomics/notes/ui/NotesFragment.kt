package com.aryanganotra.jmceconomics.notes.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.databinding.NotesFragmentBinding
import com.aryanganotra.jmceconomics.notes.model.Tab
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.notes_fragment.*

class NotesFragment() : Fragment() {
    private lateinit var binding : NotesFragmentBinding
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
        val tab = arguments!!.get("tab") as Tab
        adapter.setTab(tab)
        retainInstance = true
        return binding.root


    }





    interface CourseClickListener  {
        fun onCourseClicked(course : Tab.Course)
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