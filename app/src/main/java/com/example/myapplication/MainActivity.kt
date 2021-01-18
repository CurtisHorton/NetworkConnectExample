package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.*
import android.net.wifi.WifiConfiguration
import android.net.wifi.WifiManager
import android.net.wifi.WifiNetworkSpecifier
import android.net.wifi.WifiNetworkSuggestion
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PatternMatcher
import android.provider.Settings
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val ssid = "SSID"
    private val password = "PASSWORD"

    private lateinit var connectivityManager:ConnectivityManager
    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            // To make sure that requests don't go over mobile data
            connectivityManager.bindProcessToNetwork(network)
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            // This is to stop the looping request for OnePlus & Xiaomi models
            connectivityManager.bindProcessToNetwork(null)
            connectivityManager.unregisterNetworkCallback(this)
            // Here you can have a fallback option to show a 'Please connect manually' page with an Intent to the Wifi settings
            startActivity((Intent(Settings.ACTION_WIFI_SETTINGS)))
            val message = "Please connect to $ssid"
            Toast.makeText(applicationContext,message, Toast.LENGTH_LONG).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun onConnectClick (view: View)
    {
        val wifiSpecs = WifiNetworkSpecifier.Builder()
                .setSsid(ssid)
                .setWpa2Passphrase(password)
                .build()
        val networkRequest = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                // Add the below 2 lines if the network should have internet capabilities.
                // Adding/removing other capabilities has made no known difference so far
                //    .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                //    .addCapability(NetworkCapabilities.NET_CAPABILITY_NOT_RESTRICTED)
                .setNetworkSpecifier(wifiSpecs)
                .build()

        connectivityManager.requestNetwork(networkRequest, networkCallback)

        Reply.text = "Connect Clicked"
    }
}