package com.aryanganotra.jmceconomics.notes

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.articles.PostFragment
import com.aryanganotra.jmceconomics.notes.ui.NotesFragment

class TabAdapter (fm : FragmentManager,var behavior : Int,val context : Context ) : FragmentPagerAdapter(fm ,behavior) {
    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2,
        R.string.tab_text_3,
        R.string.tab_text_4,
        R.string.tab_text_5,
        R.string.tab_text_6

    )

    override fun getItem(position: Int): Fragment {
       val notesFragment : Fragment = NotesFragment()
        val bundle = Bundle()
       bundle.putInt("tab",position)
       notesFragment.arguments=bundle
        return notesFragment
    }

    override fun getCount(): Int {

       return TAB_TITLES.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])

    }




}