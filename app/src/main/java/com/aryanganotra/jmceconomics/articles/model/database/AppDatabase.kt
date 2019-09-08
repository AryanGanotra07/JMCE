package com.aryanganotra.jmceconomics.articles.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.aryanganotra.jmceconomics.articles.model.AData
import com.aryanganotra.jmceconomics.articles.model.PostDao

@Database(entities = arrayOf(AData::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao():PostDao
}