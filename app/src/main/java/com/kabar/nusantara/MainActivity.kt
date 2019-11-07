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
import com.google.gson.Gson
import com.kabar.nusantara.network.APIClient
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import retrofit2.Callback
import retrofit2.Response

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
                initData(token_fcm)
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

    fun initData(token: String){
        APIClient().services.registrasi(token).enqueue(object : Callback<String> {
            override fun onResponse(call: retrofit2.Call<String>, response: Response<String>) {
                println(response.body())
//                if(response.body() =="berhasil") {
//                    println("berhasil")
//                }else{
//                    println("berhasil")
//                }
            }
            override fun onFailure(call: retrofit2.Call<String>, t: Throwable) {
                println("Network failur "+ t)
            }
        })
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
                    webview.loadUrl("http://redaksi.kabarnusantara.co.id/kontribusi")
                }
                R.id.navigation_event-> {
                    loading.visibility = View.VISIBLE
                    webview.loadUrl("http://redaksi.kabarnusantara.co.id/event")
                }
                R.id.navigation_redaksi-> {
                    loading.visibility = View.VISIBLE
                    webview.loadUrl("http://redaksi.kabarnusantara.co.id/redaksi")
                }
            }
            true
        }
        val link = intent.getStringExtra("link") ?:"default"
        println("link "+link)
        if(link=="default"){
            navigation.selectedItemId = R.id.navigation_home
        }else{
            webview.loadUrl(link)
        }
    }
}
