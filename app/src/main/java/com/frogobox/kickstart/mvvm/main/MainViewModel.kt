package com.frogobox.kickstart.mvvm.main

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.frogobox.coreapi.news.NewsConstant
import com.frogobox.coreapi.news.NewsUrl
import com.frogobox.coreapi.news.response.ArticleResponse
import com.frogobox.kickstart.core.BaseViewModel
import com.frogobox.kickstart.source.ProjectDataRepository
import com.frogobox.kickstart.source.ProjectDataSource
import com.frogobox.sdk.FrogoMutableLiveData
import kotlinx.coroutines.launch

/*
 * Created by Faisal Amir
 * =========================================
 * android-kick-start-project-template
 * Copyright (C) 28/04/2020.      
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * -----------------------------------------
 * FrogoBox Inc
 * com.frogobox.kickstart.viewmodel
 * 
 */
class MainViewModel(
    private val context: Application,
    private val repository: ProjectDataRepository
) :
    BaseViewModel(context) {

    var topHeadlineLive = FrogoMutableLiveData<ArticleResponse>()

    fun getTopHeadline() {
        viewModelScope.launch {
            repository.getTopHeadline(
                NewsUrl.API_KEY,
                null,
                null,
                null,
                NewsConstant.COUNTRY_ID,
                null,
                null,
                object : ProjectDataSource.GetRemoteCallback<ArticleResponse> {
                    override fun onShowProgress() {
                        eventShowProgress.postValue(true)
                    }

                    override fun onHideProgress() {
                        eventShowProgress.postValue(false)
                    }

                    override fun onSuccess(data: ArticleResponse) {
                        topHeadlineLive.postValue(data)
                    }

                    override fun onFailed(statusCode: Int, errorMessage: String?) {
                        eventFailed.postValue(errorMessage)
                    }

                    override fun onEmptyData(check: Boolean) {
                        eventEmpty.postValue(check)
                    }
                }
            )
        }
    }
}