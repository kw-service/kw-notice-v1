package dev.yjyoon.kwnotice.view.notice.webview

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.yjyoon.kwnotice.databinding.ActivityWebviewBinding

class WebViewActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWebviewBinding.inflate(layoutInflater) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val url = intent.getStringExtra("url")

        val webView = binding.noticeWebView

        webView.settings.apply {
            this.javaScriptEnabled = true
            this.loadWithOverviewMode = true
            this.useWideViewPort = true
        }

        webView.loadUrl(url!!)
    }
}