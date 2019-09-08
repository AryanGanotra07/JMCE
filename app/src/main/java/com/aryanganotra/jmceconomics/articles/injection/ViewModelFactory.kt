package com.aryanganotra.jmceconomics.articles.injection

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.aryanganotra.jmceconomics.articles.model.database.AppDatabase
import com.aryanganotra.jmceconomics.articles.ui.post.PostListViewModel

class ViewModelFactory(private val activity: AppCompatActivity) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
      if (modelClass.isAssignableFrom(PostListViewModel::class.java)){
          val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "posts").build()
          @Suppress("UNCHECKED_CAST")
          return PostListViewModel(db.postDao() , activity.application) as T
      }
        throw IllegalAccessException("Unknown ViewModel Class")
    }
}