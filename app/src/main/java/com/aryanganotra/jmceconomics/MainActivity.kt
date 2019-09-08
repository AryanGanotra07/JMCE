package com.aryanganotra.jmceconomics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.aryanganotra.jmceconomics.articles.PostListActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        startActivity(Intent(this, PostListActivity::class.java))
        finish()



    }
}
