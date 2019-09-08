package com.aryanganotra.jmceconomics.notes.ui

import android.app.Activity
import android.app.Application
import android.app.DownloadManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
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
import com.aryanganotra.jmceconomics.notes.model.Tab
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.Executors
import java.util.jar.Manifest

class NotesListViewModel (private val application: Application , private val fragmentManager : FragmentManager) : ViewModel() {
    companion object {
        private val instance = FirebaseDatabase.getInstance()
        private val pers = instance.setPersistenceEnabled(true)
        private val tabs =arrayListOf<Tab>()

    }

    private val NOTES_REF = instance.reference

    private val liveData: FirebaseQueryLiveData = FirebaseQueryLiveData(NOTES_REF)

    private val tabLiveData: MediatorLiveData<List<Tab>> = MediatorLiveData()

    private val executors = Executors.newCachedThreadPool()
    private lateinit var request : DownloadManager.Request
    private val manager = application.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

 /*   private val adapter : TabAdapter = TabAdapter(fragmentManager , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT)

    fun getAdapter():TabAdapter {
        return adapter
    }
*/
    val loadingVisibility : MutableLiveData<Int> = MutableLiveData()






    fun startDownload (note : Tab.Course.Note ){

        request = DownloadManager.Request(Uri.parse(note.downloadUrl))
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        request.setTitle(note.noteName)
        request.setDescription(application.resources.getString(R.string.app_name))
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,"${System.currentTimeMillis()}")
        request.allowScanningByMediaScanner()
        executors.execute {
            manager.enqueue(request)
        }
        Toast.makeText(application,"Download queued",Toast.LENGTH_LONG).show()



    }








    @NonNull
    fun getTabLiveData(): LiveData<List<Tab>> {
        return tabLiveData


    }

    init{

        loadingVisibility.value= View.VISIBLE

        tabLiveData.addSource(liveData, object : Observer<DataSnapshot> {
            override fun onChanged(t: DataSnapshot?) {
                if (t != null) {
                    tabs.clear()
                    executors.execute(Runnable {
                        
                        t.children.forEach {
                          tabs.add(it.getValue(Tab::class.java)!!)

                       }



                       tabLiveData.postValue(tabs)
                        loadingVisibility.postValue(View.GONE)





                    })
                }
                else
                    tabLiveData.value = null

            }

        })

    }

}