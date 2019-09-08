package com.aryanganotra.jmceconomics.articles.ui

import android.text.Html
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Switch
import androidx.appcompat.widget.SwitchCompat
import androidx.core.text.HtmlCompat
import androidx.lifecycle.MutableLiveData
import com.aryanganotra.jmceconomics.articles.base.BaseViewModel
import com.aryanganotra.jmceconomics.articles.model.AData
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class PostViewModel : BaseViewModel() {

    private val postTitle = MutableLiveData<String>()
    private val postBody = MutableLiveData<String>()

    private val postDate = MutableLiveData<String>()

    fun bind(post : AData){
        postTitle.value= HtmlCompat.fromHtml(post.title.titleString,Html.FROM_HTML_MODE_LEGACY).toString()
        postBody.value=post.content.contentString
        postDate.value=toDate(HtmlCompat.fromHtml(post.date,Html.FROM_HTML_MODE_LEGACY).toString())





    }

    fun getPostTitle() : MutableLiveData<String>{
        return postTitle
    }

    fun  getPostBody() : MutableLiveData<String>{

        return postBody

    }

    fun getPostDate() : MutableLiveData<String>{
        return postDate
    }

    fun toDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        var myDate: Date? = null
        try {
            myDate = dateFormat.parse(dateString)

        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val reqDateFormat = SimpleDateFormat("dd/MM/yyyy")
        return reqDateFormat.format(myDate)
    }
}