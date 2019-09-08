package com.aryanganotra.jmceconomics.notes.ui

import android.app.Activity
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.databinding.ActivityNotesBinding
import com.aryanganotra.jmceconomics.notes.TabAdapter
import com.aryanganotra.jmceconomics.notes.model.Tab
import com.aryanganotra.jmceconomics.notes.ui.noteslist.NotesListFragment
import com.aryanganotra.jmceconomics.notes.ui.noteslist.NotesViewModelFactory

class NotesActivity : AppCompatActivity(), NotesFragment.CourseClickListener , NoteClickListener {
    override fun onNoteClick(note: Tab.Course.Note) {
        if (CheckPermission()) {
            if (!note.downloadUrl.isNullOrEmpty())
            viewModel.startDownload(note)
            else
                Toast.makeText(this@NotesActivity,"Download link not available",Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(this@NotesActivity, "Enable storage permission to download",Toast.LENGTH_LONG).show()
        }

    }



    private fun CheckPermission():Boolean{

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_CONTACTS)

            return false

        }
        else{
            return true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_READ_CONTACTS -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }


    override fun onCourseClicked(course: Tab.Course) {
val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment  = NotesListFragment()
        val bundle = Bundle()
        bundle.putParcelable("course" ,course)
        fragment.arguments = bundle
        fragmentTransaction.setCustomAnimations(R.anim.design_bottom_sheet_slide_in,R.anim.design_bottom_sheet_slide_out,R.anim.abc_popup_enter,R.anim.abc_tooltip_exit)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.add(R.id.frame,fragment)
        fragmentTransaction.commit()
    }

    companion object {

        private val MY_PERMISSIONS_REQUEST_READ_CONTACTS: Int = 2
    }

    private lateinit var viewModel: NotesListViewModel
    private val fragmentManager = supportFragmentManager
    private lateinit var binding:ActivityNotesBinding
    lateinit var viewPager: ViewPager
    private val adapter : TabAdapter = TabAdapter(supportFragmentManager , 6)
    private lateinit var  sharedPrefEditor : SharedPreferences.Editor
    private lateinit var sharedPref : SharedPreferences

    private fun setModePref(mode : Int){
        sharedPrefEditor.putInt("appmode",mode)
        sharedPrefEditor.apply()
    }

    private fun getModePred() : Int {
        return sharedPref.getInt("appmode",AppCompatDelegate.MODE_NIGHT_NO)
    }


    fun getAdapter() : TabAdapter {
        return adapter
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        Log.i("Activity","Called")

        sharedPref =  getSharedPreferences("mode", Context.MODE_PRIVATE)

        sharedPrefEditor = sharedPref.edit()

        AppCompatDelegate.setDefaultNightMode(getModePred())


        //AppCompatDelegate.setDefaultNightMode(getSharedPreferences("mode", Context.MODE_PRIVATE).getInt("appmode",AppCompatDelegate.MODE_NIGHT_NO))

      CheckPermission()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_notes)
        viewModel = ViewModelProviders.of(this,NotesViewModelFactory(this.application,this)).get(NotesListViewModel:: class.java)


        binding.activity = this
        binding.viewModel = viewModel
        viewPager = binding.viewPager
        viewPager.adapter = this.getAdapter()
        binding.tabLayout.setupWithViewPager(viewPager)

        viewModel.getTabLiveData().observe(this , Observer {
            getAdapter().setTabs(it)
        })







        setSupportActionBar(binding.toolbar)

        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)








    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val nightMode = AppCompatDelegate.getDefaultNightMode()
        if (nightMode == AppCompatDelegate.MODE_NIGHT_YES){
            menu!!.findItem(R.id.mode).setTitle(resources.getString(R.string.lightmode))


        }
        else {
            menu!!.findItem(R.id.mode).setTitle(resources.getString(R.string.darkmode))
        }

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {


        if (item!!.itemId == R.id.mode) {
            val nightMode = AppCompatDelegate.getDefaultNightMode()
            if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setModePref(AppCompatDelegate.MODE_NIGHT_NO)

            }
            else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                setModePref(AppCompatDelegate.MODE_NIGHT_YES)
            }


            recreate()
        }

        else if(item!!.itemId == R.id.contact){

            val ft = fragmentManager.beginTransaction()
            ft.setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out, R.anim.abc_popup_enter, R.anim.abc_popup_exit)
            ft.addToBackStack(null)
            ft.add(R.id.frame, ContactFragment())

            ft.commit()


        }

        else if(item!!.itemId == android.R.id.home){
            onBackPressed()
        }

        return true

    }

    override fun onSupportNavigateUp(): Boolean {
      onBackPressed()
        return true
    }


    override fun onAttachFragment(fragment: Fragment) {
        if(fragment is NotesFragment){
            fragment.setCourseClickListener(this)
        }
        else if(fragment is NotesListFragment){
            fragment.setOnNoteClickListener(this)

        }
    }





    private fun download(uri : Uri){
        val request = DownloadManager.Request(uri)
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle("MyFile")
        request.setDescription("Description")
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${System.currentTimeMillis()}")
        request.allowScanningByMediaScanner()
        val manager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        manager.enqueue(request)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
