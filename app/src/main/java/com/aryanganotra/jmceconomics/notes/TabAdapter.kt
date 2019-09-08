package com.aryanganotra.jmceconomics.notes

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aryanganotra.jmceconomics.articles.PostFragment
import com.aryanganotra.jmceconomics.notes.model.Tab
import com.aryanganotra.jmceconomics.notes.ui.NotesFragment

class TabAdapter (fm : FragmentManager,var behavior : Int ) : FragmentPagerAdapter(fm ,behavior) {
    private lateinit var tabs : List<Tab>

    fun setTabs( tabs : List<Tab> ){

        this.tabs = tabs
        Log.i("Called","Yes")
        notifyDataSetChanged()
    }

    override fun getItem(position: Int): Fragment {
       val notesFragment : Fragment = NotesFragment()
        val bundle = Bundle()
        bundle.putParcelable("tab",tabs.get(position))
        notesFragment.arguments=bundle
        return notesFragment
    }

    override fun getCount(): Int {

        if (::tabs.isInitialized) {
            return tabs.size
        }
        else{
            return 0
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return tabs.get(position).year

    }




}