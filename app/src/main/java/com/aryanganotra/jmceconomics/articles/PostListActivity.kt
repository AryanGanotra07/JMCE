package com.aryanganotra.jmceconomics.articles

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aryanganotra.jmceconomics.articles.ui.post.PostListViewModel
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.articles.injection.ViewModelFactory
import com.aryanganotra.jmceconomics.articles.model.AData
import com.aryanganotra.jmceconomics.articles.ui.post.PostClickListener
import com.aryanganotra.jmceconomics.databinding.ActivityPostListBinding
import com.aryanganotra.jmceconomics.notes.ui.ContactFragment
import com.aryanganotra.jmceconomics.notes.ui.NotesActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseApp

class PostListActivity: AppCompatActivity(), PostClickListener {
    override fun OnClick(post: AData) {
        val postBundle = Bundle()
        postBundle.putParcelable("post", post)
        postFragment.arguments = postBundle
        var fragmentTransaction = fragmentManager.beginTransaction() as FragmentTransaction
        fragmentTransaction.setCustomAnimations(R.anim.abc_grow_fade_in_from_bottom,R.anim.abc_shrink_fade_out_from_bottom, R.anim.abc_popup_enter,R.anim.abc_popup_exit)
        fragmentTransaction.replace(R.id.frame, postFragment)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
    }

    private lateinit var binding: ActivityPostListBinding
    private lateinit var viewModel: PostListViewModel
    private var errorSnackbar : Snackbar? =null
    private val fragmentManager : FragmentManager = supportFragmentManager
    private lateinit var  sharedPrefEditor : SharedPreferences.Editor
    private lateinit var sharedPref : SharedPreferences
   private val postFragment:PostFragment = PostFragment()


    companion object{
        private val fburl = "https://www.facebook.com/contrarianjmc/"
        private val instaurl = "https://www.instagram.com/thecontrarian_jmc/?hl=en"


    }


       private fun setModePref(mode : Int){
        sharedPrefEditor.putInt("appmode",mode)
        sharedPrefEditor.apply()
    }

    private fun getModePred() : Int {
        return sharedPref.getInt("appmode",AppCompatDelegate.MODE_NIGHT_NO)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        sharedPref =  getSharedPreferences("mode", Context.MODE_PRIVATE)

        sharedPrefEditor = sharedPref.edit()

       AppCompatDelegate.setDefaultNightMode(getModePred())




        FirebaseApp.initializeApp(applicationContext)



        binding = DataBindingUtil.setContentView(this , R.layout.activity_post_list)
        binding.postList.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        binding.facebook.setOnClickListener(onClickListener)
        binding.instagram.setOnClickListener(onClickListener)

        setSupportActionBar(binding.toolbar)
     //   supportActionBar!!.setDisplayHomeAsUpEnabled(true)
     //   supportActionBar!!.setDisplayShowHomeEnabled(true)




        viewModel = ViewModelProviders.of(this,ViewModelFactory(this)).get(PostListViewModel::class.java)

        binding.viewModel = viewModel

        viewModel.postListAdapter.setListener(this)

        viewModel.errorMessage.observe(this, Observer {
            errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })


        viewModel.changeActivity.observe(this, Observer {
            changeActivity -> if (changeActivity) {
            startActivity(Intent(this, NotesActivity::class.java))
        }
        })








/*
        viewModel.loadPost.observe(this,Observer {
            loadPost ->



        })

        */






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

    private val onClickListener = View.OnClickListener {
        when(it){
            binding.facebook -> openLink(fburl)
            binding.instagram -> openLink(instaurl)

        }
    }

    private fun openLink(url : String){
        val i : Intent = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(i)
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction(R.string.retry, viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    override fun onRestart() {
        super.onRestart()
        Log.i("Restart","yes")
    }
}