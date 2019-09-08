package com.aryanganotra.jmceconomics.articles.ui.post

import android.app.Application
import android.app.SharedElementCallback
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR.viewModel
import androidx.recyclerview.widget.RecyclerView

import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.articles.model.AData
import com.aryanganotra.jmceconomics.articles.ui.PostViewModel
import com.aryanganotra.jmceconomics.databinding.ItemPostBinding
import java.util.logging.Filter

class PostListAdapter( private val application: Application) : RecyclerView.Adapter<PostListAdapter.ViewHolder>() {
    private lateinit var postList: List<AData>
    private lateinit var postListSuggestions: List<AData>
    private lateinit var postClickListener : PostClickListener
   private  val animation : Animation = AnimationUtils.loadAnimation(application.applicationContext , R.anim.abc_slide_in_bottom)


   fun setListener(callback: PostClickListener){

       this.postClickListener = callback

   }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      holder.bind(postList[position],postClickListener)





    }

    override fun getItemCount(): Int {
     return if (::postList.isInitialized) postList.size else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding:ItemPostBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_post, parent, false)
            binding.root.startAnimation(animation)
        return ViewHolder(binding)


    }

    fun updatePostList(postList:List<AData>){
        this.postList = postList
        this.postListSuggestions = postList
        notifyDataSetChanged()

    }






    class ViewHolder(private val binding : ItemPostBinding):RecyclerView.ViewHolder(binding.root) {
        private val viewModel = PostViewModel()
        fun bind(post: AData,postClickListener: PostClickListener){
            viewModel.bind(post)
            binding.viewModel= viewModel
            itemView.setOnClickListener {
                Log.i("OnClick",post.title.titleString)
                if (postClickListener!=null) {
                    postClickListener.OnClick(post)
                }
            }


        }






    }


    val filter : android.widget.Filter = object : android.widget.Filter() {

        override fun performFiltering(constraint: CharSequence?): FilterResults {


            val suggestions = arrayListOf<AData>()
            val results : FilterResults = FilterResults()

            if(::postList.isInitialized) {
                if (!constraint.isNullOrEmpty()) {

                    postListSuggestions.listIterator().forEach {
                        if (it.title.titleString.toLowerCase().contains(constraint.toString().toLowerCase())) {
                            suggestions.add(it)
                        }
                    }

                    results.values = suggestions
                    results.count = suggestions.size

                }


                if (suggestions.isEmpty()) {
                    results.values = postListSuggestions
                    results.count = postListSuggestions.size

                }

            }






            return results



        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            if (results != null && results.count>0) {
                postList=(results.values as List<AData>)
                notifyDataSetChanged()

            }
        }

    }




}
