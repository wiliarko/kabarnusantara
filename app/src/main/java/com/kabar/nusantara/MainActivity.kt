package com.kabar.nusantara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var token_fcm=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this);

        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    println( "getInstanceId failed")
                    return@OnCompleteListener
                }
                token_fcm = task.result?.token.toString()
                println(token_fcm)
            })

        initAction(savedInstanceState)
        webview.settings.javaScriptEnabled = true
        webview.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                loading.visibility = View.GONE
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // Check if the key event was the Back button and if there's history
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event)
    }

    fun initAction(savedInstanceState: Bundle?){
        navigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    loading.visibility = View.VISIBLE
                    webview.loadUrl("https://www.kabarnusantara.co.id/")
                }
                R.id.navigation_kontribusi-> {
                    loading.visibility = View.VISIBLE
                    webview.loadUrl("https://www.kabarnusantara.co.id/p/kirim-berita-ke-kabar-nusantara.html")
                }
                R.id.navigation_event-> {
                    loading.visibility = View.VISIBLE
                    webview.loadUrl("https://www.kabarnusantara.co.id/#PopularPosts1")
                }
                R.id.navigation_redaksi-> {
                    loading.visibility = View.VISIBLE
                    webview.loadUrl("https://www.kabarnusantara.co.id/search/label/Label2?max-results=10")
                }
            }
            true
        }
        navigation.selectedItemId = R.id.navigation_home
    }
}
