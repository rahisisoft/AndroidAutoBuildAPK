package com.example.androidautobuildapk

import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.*
import android.os.IBinder
import android.os.RemoteException
import androidx.appcompat.app.AppCompatActivity
import com.sunmi.extprinterservice.IPrinterService

class MainActivity : AppCompatActivity() {

    private var printerService: IPrinterService? = null

    private val connService = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            printerService = IPrinterService.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            printerService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val webView = WebView(this)
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        webView.addJavascriptInterface(WebAppInterface(), "AndroidPrinter")

        webView.loadUrl("https://your-web-app.com") // 🔁 Replace with your URL
        setContentView(webView)

        val intent = Intent("com.sunmi.service.PrinterService")
        intent.setPackage("com.sunmi.extprinterservice")
        bindService(intent, connService, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(connService)
    }

    inner class WebAppInterface {
        @JavascriptInterface
        fun printText(text: String) {
            try {
                printerService?.printText(text, null)
                printerService?.lineWrap(2, null)
            } catch (e: RemoteException) {
                e.printStackTrace()
            }
        }
    }
}
