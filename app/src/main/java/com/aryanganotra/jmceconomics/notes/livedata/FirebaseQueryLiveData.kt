package com.aryanganotra.jmceconomics.notes.livedata

import androidx.lifecycle.LiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import java.util.logging.Handler

class FirebaseQueryLiveData( val ref : DatabaseReference) : LiveData<DataSnapshot>() {

    private var listenerRemovePending = false
    private val handler = android.os.Handler()
    private val removeListener = Runnable {
        ref.removeEventListener(MyValueEventListener)
        listenerRemovePending = false
    }


    override fun onActive() {
        if (listenerRemovePending) {
            handler.removeCallbacks(removeListener)
        }
        else {
            ref.addValueEventListener(MyValueEventListener)
        }
        listenerRemovePending = false

    }

    override fun onInactive() {

        handler.postDelayed(removeListener, 2000)
        listenerRemovePending = true
    }

   private val MyValueEventListener : ValueEventListener = object : ValueEventListener{

        override fun onCancelled(p0: DatabaseError) {

        }

        override fun onDataChange(p0: DataSnapshot) {
            value = p0
        }


    }



}