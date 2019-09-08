package com.aryanganotra.jmceconomics.articles.injection.component

import com.aryanganotra.jmceconomics.articles.injection.module.NetworkModule
import com.aryanganotra.jmceconomics.articles.ui.post.PostListViewModel
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [NetworkModule::class])

interface ViewModelInjector {


    fun inject(postListViewModel: PostListViewModel)

    @Component.Builder
    interface Builder{

        fun build():ViewModelInjector

        fun networkModule(networkModule: NetworkModule):Builder

    }
}