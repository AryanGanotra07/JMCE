package com.aryanganotra.jmceconomics.articles.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PostDao {
    @get:Query("SELECT * FROM adata ORDER BY date desc")
    val all: List<AData>

    @Insert
    fun insertAll(vararg  posts: AData)

    @Query("DELETE FROM adata")
    fun deleteAll()


}