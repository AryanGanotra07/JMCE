package com.aryanganotra.jmceconomics.notes.ui.noteslist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.databinding.NotesListFragmentBinding
import com.aryanganotra.jmceconomics.notes.model.Note
import com.aryanganotra.jmceconomics.notes.ui.NoteClickListener
import com.aryanganotra.jmceconomics.notes.ui.NotesListViewModel
import com.aryanganotra.jmcemanager.model.Course

class NotesListFragment : Fragment() {

    private lateinit var binding : NotesListFragmentBinding
    private lateinit var adapter : NotesListAdapter
    private lateinit var callback : NoteClickListener
    private lateinit var notesViewModel: NotesListViewModel

    fun setOnNoteClickListener (callback : NoteClickListener){
        this.callback = callback
        this.adapter = NotesListAdapter(callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    companion object {
        private val mailBody = "Please upload the attached note to the JMC Economics app"
        private val subject = "Notes-Upload"
        private val emailids = arrayOf("jmceconomicscouncil@gmail.com")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if(isAdded)
            AppCompatDelegate.setDefaultNightMode(activity?.getSharedPreferences("mode", Context.MODE_PRIVATE)!!.getInt("appmode",
                AppCompatDelegate.MODE_NIGHT_NO))

        binding = DataBindingUtil.inflate(inflater , R.layout.notes_list_fragment, container, false)
        binding.viewModel = this

        binding.recyclerView.layoutManager = LinearLayoutManager(context , RecyclerView.VERTICAL, false)
        val course = arguments!!.get("course") as Course
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
        binding.titleToolbar.text = course.courseName

        binding.sendNote.setOnClickListener(onClickListener)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val course = arguments!!.get("course") as Course

        notesViewModel = ViewModelProviders.of(this,
            NotesViewModelFactory(activity!!.application,context as AppCompatActivity)
        ).get(NotesListViewModel:: class.java)
        notesViewModel.getNotesLiveData().observe(this, Observer {
            if (it!=null)
            {
                val notes = it.filter { note -> note.subId == course.id }
                adapter.setNotesList(notes as ArrayList<Note>)
            }
            else
            {
                adapter.setNotesList(ArrayList())
            }
        })
    }

    private val onClickListener : View.OnClickListener = View.OnClickListener {
        when (it){
            binding.sendNote -> composeEmail(emailids, subject)

        }
    }

   private fun composeEmail(addresses: Array<String>, subject: String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, addresses)
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, mailBody )
        }

       startActivity(intent)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu?.clear()
    }



    fun getNotesListAdapter() : NotesListAdapter{
        return adapter
    }




}