package com.frogobox.kickstart.mvvm.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.frogobox.kickstart.R
import com.frogobox.kickstart.base.view.ui.BaseActivity
import com.frogobox.kickstart.databinding.ActivityMainBinding
import com.frogobox.kickstart.mvvm.detail.DetailActivity
import com.frogobox.kickstart.source.model.Article
import com.frogobox.recycler.boilerplate.viewrclass.FrogoViewAdapterCallback
import kotlinx.android.synthetic.main.list_news_article_vertical.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(baseLayoutInflater())
        setContentView(activityMainBinding.root)
        setupViewModel()
    }

    private fun setupViewModel() {
        mainViewModel.apply {

            usingChuck()
            getTopHeadline()
            topHeadlineLive.observe(this@MainActivity, {
                it.articles?.let { it1 -> setupRvNews(it1) }
            })

            eventShowProgress.observe(this@MainActivity, {
                setupEventProgressView(activityMainBinding.progressView, it)
            })

        }

    }

    private fun setupRvNews(data: List<Article>) {

        val newsAdapter = object : FrogoViewAdapterCallback<Article> {
            override fun onItemClicked(data: Article) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra("EXTRA_DATA_ARTICLE", data)
                startActivity(intent)
            }

            override fun onItemLongClicked(data: Article) {

            }

            override fun setupInitComponent(view: View, data: Article) {
                view.tv_title.text = data.title
                view.tv_description.text = data.publishedAt
                view.tv_published.text = data.description
                Glide.with(view.context).load(data.urlToImage).into(view.iv_url)
            }
        }

        activityMainBinding.rvNews
            .injector<Article>()
            .addData(data)
            .addCustomView(R.layout.list_news_article_vertical)
            .addEmptyView(null)
            .addCallback(newsAdapter)
            .createLayoutLinearVertical(false)
            .build()
    }

}
