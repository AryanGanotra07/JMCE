package com.aryanganotra.jmceconomics.articles.utils

import android.view.View
import android.webkit.WebView
import android.widget.CompoundButton
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager.widget.ViewPager
import com.aryanganotra.jmceconomics.articles.utils.extensions.getParentActivity
import com.aryanganotra.jmceconomics.notes.TabAdapter
import com.google.android.material.tabs.TabLayout
import okio.Utf8

@BindingAdapter("mutableVisibility")
fun setMutableVisibility( view : View, visibility: MutableLiveData<Int>){

    val parentActivity : AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?: View.VISIBLE })

    }

}



@BindingAdapter("mutableText")
fun setMutableTest(view: TextView, text:MutableLiveData<String>?) {

    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null){
        text.observe(parentActivity, Observer { value -> view.text =value?:"" })
    }


}


@BindingAdapter("adapter")

fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>){
    view.adapter = adapter
}


@BindingAdapter("loadData")
fun loadData(view: WebView, text: String){
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null){
        var html = "<style>img{display: inline;height: auto;max-width: 100%;}*{font: 20px/40px Cormorant, serif; }iframe{display: inline;height: auto;max-width: 100%;}</style>"
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
            html = "<style>body{background: #212121; color: white;} img{display: inline;height: auto;max-width: 100%;}*{font: 20px/40px Cormorant, serif; }iframe{display: inline;height: auto;max-width: 100%;}</style>"
        }
         view.loadData(html+text,"text/html","UTF-8")
       view.settings.javaScriptEnabled = true
    }
}

@BindingAdapter("queryTextChangeListener")
fun setQueryTextChangeListener(view : androidx.appcompat.widget.SearchView, listener : androidx.appcompat.widget.SearchView.OnQueryTextListener){
    view.setOnQueryTextListener(listener)
}

@BindingAdapter("isRefreshing")
fun isRefreshing(view: SwipeRefreshLayout, boolean: MutableLiveData<Boolean>){

    val parentActivity : AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && boolean != null) {
        boolean.observe(parentActivity, Observer { value -> view.isRefreshing = value?: false })

    }

}

@BindingAdapter("refreshListener")
fun refreshListener(view: SwipeRefreshLayout, listener : SwipeRefreshLayout.OnRefreshListener){
    if (listener != null) {
        view.setOnRefreshListener(listener)
    }
}

@BindingAdapter("onCheckedChange")
fun onCheckedChange(view: SwitchCompat , listener: CompoundButton.OnCheckedChangeListener){
    if (listener!=null){
        view.setOnCheckedChangeListener(listener)
    }


}

@BindingAdapter("setupAdapter")
fun setupAdapter(view : ViewPager , adapter : TabAdapter) {
    if (adapter != null){
        view.adapter = adapter
    }
}


@BindingAdapter("setupViewPager")
fun setupViewPager(view : TabLayout , viewPager : ViewPager){
    view.setupWithViewPager(viewPager)
}

