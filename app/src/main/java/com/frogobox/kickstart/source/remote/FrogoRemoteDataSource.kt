package com.frogobox.kickstart.source.remote

import com.frogobox.kickstart.source.model.ArticleResponse
import com.frogobox.kickstart.source.model.Favorite
import com.frogobox.kickstart.source.model.SourceResponse
import com.frogobox.kickstart.source.FrogoDataSource
import com.frogobox.kickstart.source.remote.network.FrogoApiClient
import com.frogobox.kickstart.util.SingleFunc.Func.noAction

/**
 * Created by Faisal Amir
 * FrogoBox Inc License
 * =========================================
 * PublicSpeakingBooster
 * Copyright (C) 16/08/2019.
 * All rights reserved
 * -----------------------------------------
 * Name     : Muhammad Faisal Amir
 * E-mail   : faisalamircs@gmail.com
 * Github   : github.com/amirisback
 * LinkedIn : linkedin.com/in/faisalamircs
 * -----------------------------------------
 * FrogoBox Software Industries
 * com.frogobox.publicspeakingbooster.source.remote
 *
 */
object FrogoRemoteDataSource : FrogoDataSource {

    override suspend fun getTopHeadline(
        apiKey: String,
        q: String?,
        sources: String?,
        category: String?,
        country: String?,
        pageSize: Int?,
        page: Int?,
        callback: FrogoDataSource.GetRemoteCallback<ArticleResponse>
    ) {
        val serviceApiClient = FrogoApiClient.create().getTopHeadline(
            apiKey,
            q,
            sources,
            category,
            country,
            pageSize,
            page
        )

        callback.onShowProgressDialog()
        if (serviceApiClient.isSuccessful) {
            if (serviceApiClient.body() != null) {
                callback.onSuccess(serviceApiClient.body()!!)
            } else {
                callback.onEmptyData()
            }
            callback.onHideProgressDialog()
        } else {
            callback.onFailed(500, serviceApiClient.message())
            callback.onHideProgressDialog()
        }

    }

    override suspend fun getEverythings(
        apiKey: String,
        q: String?,
        from: String?,
        to: String?,
        qInTitle: String?,
        sources: String?,
        domains: String?,
        excludeDomains: String?,
        language: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?,
        callback: FrogoDataSource.GetRemoteCallback<ArticleResponse>
    ) {
        val serviceApiClient = FrogoApiClient.create().getEverythings(
            apiKey,
            q,
            from,
            to,
            qInTitle,
            sources,
            domains,
            excludeDomains,
            language,
            sortBy,
            pageSize,
            page
        )
        callback.onShowProgressDialog()
        if (serviceApiClient.isSuccessful) {
            if (serviceApiClient.body() != null) {
                callback.onSuccess(serviceApiClient.body()!!)
            } else {
                callback.onEmptyData()
            }
            callback.onHideProgressDialog()
        } else {
            callback.onFailed(500, serviceApiClient.message())
            callback.onHideProgressDialog()
        }
    }

    override suspend fun getSources(
        apiKey: String,
        language: String,
        country: String,
        category: String,
        callback: FrogoDataSource.GetRemoteCallback<SourceResponse>
    ) {
        val serviceApiClient =
            FrogoApiClient.create().getSources(apiKey, language, country, category)
        callback.onShowProgressDialog()
        if (serviceApiClient.isSuccessful) {
            if (serviceApiClient.body() != null) {
                callback.onSuccess(serviceApiClient.body()!!)
            } else {
                callback.onEmptyData()
            }
            callback.onHideProgressDialog()
        } else {
            callback.onFailed(500, serviceApiClient.message())
            callback.onHideProgressDialog()
        }
    }


    override fun saveRoomFavorite(data: Favorite): Boolean {
        return noAction()
    }


    override fun getRoomFavorite(callback: FrogoDataSource.GetLocalCallback<List<Favorite>>) {
        noAction()
    }

    override fun updateRoomFavorite(
        tableId: Int,
        title: String,
        description: String,
        dateTime: String
    ): Boolean {
        return noAction()
    }

    override fun deleteRoomFavorite(tableId: Int): Boolean {
        return noAction()
    }

    override fun nukeRoomFavorite(): Boolean {
        return noAction()
    }


}