package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun onConnectClick (view: View)
    {
        val wifiSpecs = WifiNetworkSpecifier.Builder()
            .setSsid("SSID")
            .setWpa2Passphrase("PASSWORD")
            .build()
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .setNetworkSpecifier(wifiSpecs)
            .build()
        val connectivityManager = getSystemService((Context.CONNECTIVITY_SERVICE)) as ConnectivityManager
        val networkCallback = ConnectivityManager.NetworkCallback()

        connectivityManager.requestNetwork(networkRequest, networkCallback)
        Reply.text = "Connect Clicked"

    }
}