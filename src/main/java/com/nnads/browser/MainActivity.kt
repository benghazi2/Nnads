package com.nnads.browser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import android.webkit.WebView
import android.widget.FrameLayout
import android.view.KeyEvent

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var webViewContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // تحميل التفضيلات
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val isDarkMode = preferences.getBoolean("dark_mode", false)

        // إعداد الواجهة
        webViewContainer = findViewById(R.id.webview_container)
        setupWebView()

        // تطبيق الوضع الداكن
        if (isDarkMode) {
            webView.setBackgroundColor(android.graphics.Color.BLACK)
        }

        // تحميل الصفحة الرئيسية
        webView.loadUrl("https://www.google.com")
    }

    private fun setupWebView() {
        webView = WebView(this).apply {
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
            webViewClient = NnadsWebViewClient()
            webChromeClient = NnadsWebChromeClient()
        }
        webViewContainer.addView(webView)
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}
