package com.aryanganotra.jmceconomics.articles

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.articles.injection.ViewModelFactory
import com.aryanganotra.jmceconomics.articles.model.AData
import com.aryanganotra.jmceconomics.articles.ui.PostViewModel
import com.aryanganotra.jmceconomics.articles.ui.post.PostListViewModel
import com.aryanganotra.jmceconomics.databinding.PostDisplayBinding
import kotlinx.android.synthetic.main.post_display.*

class PostFragment() : Fragment() {

    private lateinit var binding:PostDisplayBinding
   // private lateinit var content : String
    //private var normalMode :String = "<style>img{display: inline;height: auto;max-width: 100%;}*{font: 20px/40px Cormorant, serif; }iframe{display: inline;height: auto;max-width: 100%;}</style>"
    //private var readMode: String ="<style>body {background:#FBF0D9; color:#5F4B32;} img{display: inline;height: auto;max-width: 100%;}*{font: 20px/40px Cormorant, serif; }iframe{display: inline;height: auto;max-width: 100%;}</style>"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       binding =  DataBindingUtil.inflate(inflater,R.layout.post_display,container,false)
        val post = arguments!!.get("post") as AData

        val view = binding.root
        binding.post=post
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)





    }
}