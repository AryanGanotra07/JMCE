package com.aryanganotra.jmceconomics.notes.ui

import android.app.Activity
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.*
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.notes.TabAdapter
import com.aryanganotra.jmceconomics.notes.livedata.FirebaseQueryLiveData
import com.aryanganotra.jmceconomics.notes.model.Note
import com.aryanganotra.jmcemanager.model.Course
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.io.File
import java.lang.Exception
import java.util.concurrent.Executors
import java.util.jar.Manifest

class NotesListViewModel (private val application: Application , private val fragmentManager : FragmentManager) : ViewModel() {
    companion object {
        private val instance = FirebaseDatabase.getInstance()
        private val pers = instance.setPersistenceEnabled(true)
        private val courses =arrayListOf<Course>()
        private val notes =arrayListOf<Note>()

    }


    private val NOTES_REF = instance.reference.child("notes")
    private val COURSES_REF = instance.reference.child("courses")

    private val coursesliveDataRef: FirebaseQueryLiveData = FirebaseQueryLiveData(COURSES_REF)
    private val notesliveDataRef : FirebaseQueryLiveData = FirebaseQueryLiveData(NOTES_REF)

    private val coursesLiveData: MediatorLiveData<List<Course>> = MediatorLiveData()
    private val notesLiveData : MediatorLiveData<List<Note>> = MediatorLiveData()

    private val executors = Executors.newCachedThreadPool()
    private lateinit var request : DownloadManager.Request
    private val manager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

 /*   private val adapter : TabAdapter = TabAdapter(fragmentManager , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

    fun getAdapter():TabAdapter {
        return adapter
    }
*/
    val loadingVisibility : MutableLiveData<Int> = MutableLiveData()


    fun startDownload (note : Note ){

        try {
            request = DownloadManager.Request(Uri.parse(note.downloadUrl))
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
            request.setTitle(note.noteName)
            request.setDescription(application.resources.getString(R.string.app_name))
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,note.noteName+".pdf")
            request.allowScanningByMediaScanner()
            executors.execute {
                manager.enqueue(request)
            }
            Toast.makeText(application,"Download queued",Toast.LENGTH_LONG).show()
        }
        catch (e : Exception)
        {
            Toast.makeText(application,e.message.toString(),Toast.LENGTH_LONG).show()
        }

    }

    private fun getFileName(url : String) : String{
        val uri = Uri.parse(url)
        val uriString = uri.toString()
        val myFile = File(uriString)
        val path: String = myFile.getAbsolutePath()
        var displayName: String = System.currentTimeMillis().toString()+".pdf"

        if (uriString.startsWith("content://")) {
            var cursor: Cursor? = null
            try {
                cursor = application.getContentResolver().query(uri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    displayName =
                        cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor!!.close()
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName()
        }

        return displayName+".pdf"

    }


    @NonNull
    fun getCoursesLiveData(): LiveData<List<Course>> {
        return coursesLiveData
    }

    @NonNull
    fun getNotesLiveData(): LiveData<List<Note>> {
        return notesLiveData
    }


    init{

        loadingVisibility.value= View.VISIBLE

        coursesLiveData.addSource(coursesliveDataRef, Observer {
            if (it!=null && it.hasChildren()) {

                val lecturesList: ArrayList<Course> = ArrayList()
                for (lecture in it.children)
                {
                    val lect : Course = lecture.getValue(Course::class.java)!!
                    lecturesList.add(lect)
                }

                Thread(Runnable {
                    coursesLiveData.postValue(lecturesList)
                    loadingVisibility.postValue(View.GONE)
                }).start()
            }
            else coursesLiveData.value = null
        })

        notesLiveData.addSource(notesliveDataRef, Observer {
            if (it != null && it.hasChildren()) {
                val lecturesList: ArrayList<Note> = ArrayList()
                for (lecture in it.children) {
                    val lect: Note = lecture.getValue(Note::class.java)!!
                    lecturesList.add(lect)
                }

                Thread(Runnable {
                    notesLiveData.postValue(lecturesList)
                    loadingVisibility.postValue(View.GONE)
                }).start()
            }
            else notesLiveData.value = null

        })



    }

}