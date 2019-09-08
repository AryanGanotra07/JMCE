package com.aryanganotra.jmceconomics.articles.base

import androidx.lifecycle.ViewModel
import com.aryanganotra.jmceconomics.articles.injection.component.DaggerViewModelInjector

import com.aryanganotra.jmceconomics.articles.injection.component.ViewModelInjector
import com.aryanganotra.jmceconomics.articles.injection.module.NetworkModule
import com.aryanganotra.jmceconomics.articles.ui.post.PostListViewModel

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }


    private fun inject(){
        when(this) {
            is PostListViewModel -> injector.inject(this)
        }
    }


}