package com.cding.app.ui.detail

import android.content.Intent
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.cding.common.base.BaseVMActivity
import com.cding.common.base.NoViewModel
import com.cding.app.databinding.ActivityDetailBinding
import com.cding.app.ui.study.RxJavaStdActivity


/**
 * @author cding
 * @date 2020/11/11
 *
 */
class DetailActivity : BaseVMActivity<NoViewModel, ActivityDetailBinding>() {

//    override val layoutId = R.layout.activity_detail

    override fun initView(savedInstanceState: Bundle?) {
        initWebView()

        intent.getStringExtra("url")?.let {
            dataBinding.webView.loadUrl(it)
        }
    }

    override fun initData() {
        dataBinding.titleTV.setOnClickListener {
            startActivity(Intent(this@DetailActivity, RxJavaStdActivity::class.java))
        }
    }

    private fun initWebView() {
        dataBinding.webView.setInitialScale(100)
        dataBinding.webView.webViewClient = webViewClient
        val webSettings = dataBinding.webView.settings
        with(webSettings) {
            useWideViewPort = true
            loadWithOverviewMode = false
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
//            setAppCacheEnabled(true)
            cacheMode = WebSettings.LOAD_DEFAULT
            domStorageEnabled = true
            blockNetworkImage = false
            textZoom = 100
            mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
    }

    private val webViewClient = object: WebViewClient() {
        @Deprecated("Deprecated in Java")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            url?.let { view?.loadUrl(it) }
            return true
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            view?.loadUrl(request?.url.toString())
            return true
        }
    }

}
