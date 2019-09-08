package com.aryanganotra.jmceconomics.articles.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Entity
@Parcelize
data class AData(@field:PrimaryKey var id:Int, var date:String, @Embedded var title:Title, @Embedded var content:Content  ) : Parcelable {

    @Keep
    @Parcelize
    data class Title(@SerializedName("rendered")var titleString:String) : Parcelable

    @Keep
    @Parcelize
    data class Content(@SerializedName("rendered")var contentString:String) :Parcelable




}