package com.aryanganotra.jmceconomics.articles.ui.post

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import androidx.databinding.adapters.SearchViewBindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.aryanganotra.jmceconomics.R
import com.aryanganotra.jmceconomics.articles.base.BaseViewModel
import com.aryanganotra.jmceconomics.articles.model.AData
import com.aryanganotra.jmceconomics.articles.model.PostDao
import com.aryanganotra.jmceconomics.articles.network.NetworkCheck
import com.aryanganotra.jmceconomics.articles.network.PostApi
import com.aryanganotra.jmceconomics.notes.ui.NotesActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import java.lang.Exception
import javax.inject.Inject
import kotlin.random.Random

class PostListViewModel(private val postDao:PostDao, private val application: Application) : BaseViewModel(),PostClickListener {
    override fun OnClick(post: AData) {
       loadPost.value=post

    }

    @Inject
    lateinit var postApi: PostApi


    val loadingVisibility : MutableLiveData<Int> = MutableLiveData()

    val isRefreshing : MutableLiveData<Boolean> = MutableLiveData()

    lateinit var postList: List<AData>


    val queryTextChangeListener: androidx.appcompat.widget.SearchView.OnQueryTextListener = object: androidx.appcompat.widget.SearchView.OnQueryTextListener{
        override fun onQueryTextSubmit(query: String?): Boolean {

            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {

                postListAdapter.filter.filter(newText)

            return false
        }



    }



    val swipeRefreshListener : SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener {

        loadPostsFromNetwork()

    }

    val changeActivity : MutableLiveData<Boolean> = MutableLiveData()

    val changeActivityListener : View.OnClickListener = View.OnClickListener {

       application.startActivity(Intent(application , NotesActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
    }

    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadPosts() }

    val loadPost:MutableLiveData<AData> = MutableLiveData()


    val postListAdapter:PostListAdapter=PostListAdapter( application)

    private lateinit var subscription: Disposable

    private lateinit var openedSubscription : Disposable


    init {
        isRefreshing.value = false
        loadPosts()

    }



    private fun loadPostsFromDb() {

        subscription = Observable.fromCallable { postDao.all }
            .concatMap {
                    dbPostList ->

                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe({result -> onRetrievePostListSuccess(result)},
                {onRetrievePostListError()},
                {onRetrievePostListFinish()},
                {onRetrievePostListStart()}
            )

    }



    private fun loadPostsFromNetwork() {



        subscription =  postApi.getPosts("100").concatMap { apiPostList ->
            postDao.deleteAll()
            postDao.insertAll(*apiPostList.toTypedArray())
            Observable.just(apiPostList)
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({result -> onRetrievePostListSuccess(result)},
                {onRetrievePostListErrorFromNetwork()},
                { },
                {errorMessage.value = null}
            )

    }

    private fun loadPosts(){
        subscription = Observable.fromCallable { postDao.all }
            .concatMap {
                    dbPostList ->
                if(dbPostList.isEmpty())
                    postApi.getPosts("100").concatMap {
                            apiPostList ->
                        postDao.deleteAll()
                        postDao.insertAll(*apiPostList.toTypedArray())
                        Observable.just(apiPostList)
                    }
                else
                    Observable.just(dbPostList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrievePostListStart() }
            .doOnTerminate { onRetrievePostListFinish() }
            .subscribe({result -> onRetrievePostListSuccess(result)},
                { onRetrievePostListError()},
                { onRetrievePostListFinish() },
                { onRetrievePostListStart() }
            )
    }



    private fun onRetrievePostListStart() {
        loadingVisibility.value= View.VISIBLE
        errorMessage.value=null
    }

    private fun onRetrievePostListFinish() {
        loadingVisibility.value=View.GONE
        isRefreshing.value = false
        loadPostsFromNetwork()
        Log.i("NetworkLoad","Started")
    }

    private fun onRetrievePostListSuccess(postList:List<AData>) {

        isRefreshing.value = false

        Log.i("NetworkLoad","Ended with succes")
        postListAdapter.updatePostList(postList)
        this.postList = postList




    }

    private fun onRetrievePostListError() {

        errorMessage.value= R.string.post_Error

    }

    private fun onRetrievePostListErrorFromNetwork() {

        isRefreshing.value = false

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

}