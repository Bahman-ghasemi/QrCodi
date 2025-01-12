package ir.bahmanghasemi.qrcodeapp.common.view.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiNetworkSpecifier
import android.os.Build
import android.provider.Settings
import com.github.alexzhirkevich.customqrgenerator.QrData

object Connectivity {
    fun connectToWifi(context: Context, data: QrData.Wifi) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val specifier = WifiNetworkSpecifier.Builder()
                .setSsid(data.ssid!!)
                .setWpa2Passphrase(data.psk!!) // Use setIsOpenNetwork() if it's an open network
                .build()

            val request = NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .setNetworkSpecifier(specifier)
                .build()

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            connectivityManager!!.requestNetwork(request, object : NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    // Network is available, user has connected
                }
            })
        } else {
            // For older versions, open Wi-Fi settings
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(intent)
        }
    }
}