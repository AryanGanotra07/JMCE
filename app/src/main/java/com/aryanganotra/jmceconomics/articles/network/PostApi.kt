package com.aryanganotra.jmceconomics.articles.network

import com.aryanganotra.jmceconomics.articles.model.AData
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface PostApi {

     @GET("posts")
     fun getPosts(@Query("per_page")per_page:String):Observable<List<AData>>

    // ,@Query("filter[orderby]")order_by:String, @Query("order")order_type:String
}