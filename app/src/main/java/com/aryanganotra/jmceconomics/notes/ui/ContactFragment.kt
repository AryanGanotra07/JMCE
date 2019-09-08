package com.aryanganotra.jmceconomics.notes.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.articles.PostListActivity
import com.aryanganotra.jmceconomics.databinding.ContactFragmentBinding
import com.aryanganotra.jmceconomics.notes.ui.noteslist.NotesListFragment
import kotlinx.android.synthetic.main.contact_fragment.*

class ContactFragment : Fragment() {
    private lateinit var binding : ContactFragmentBinding


    companion object{

        private val fburl = "https://www.facebook.com/EconJMC/"
        private val instaurl = "https://www.instagram.com/economicsdepartmentjmc/"
        private val gmailId ="jmceconomicscouncil@gmail.com"

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

    }

    private fun composeEmail(email : String) {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        }

        startActivity(intent)

    }

    private fun openLink(url : String){
        val i : Intent = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(i)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.contact_fragment, container, false)
        if(isAdded)
        AppCompatDelegate.setDefaultNightMode(activity?.getSharedPreferences("mode", Context.MODE_PRIVATE)!!.getInt("appmode",
            AppCompatDelegate.MODE_NIGHT_NO))
        if (isAdded){
            (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
            //
            (activity as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
            //
            (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        binding.fb.setOnClickListener(onClickListener)
        binding.insta.setOnClickListener(onClickListener)
        binding.gmail.setOnClickListener(onClickListener)


        return binding.root
    }

    private val onClickListener : View.OnClickListener = View.OnClickListener {
        when(it){

            binding.fb -> openLink(fburl)
            binding.insta -> openLink(instaurl)
            binding.gmail -> composeEmail(gmailId)



        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)



    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu?.clear()

      //
        //  inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)


    }




}