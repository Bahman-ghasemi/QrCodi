package ir.bahmanghasemi.qrcodeapp.common.view.util

import com.github.alexzhirkevich.customqrgenerator.QrData

fun Int.mapToQrWifiAuthentication(): QrData.Wifi.Authentication {
    return when (this) {
        1 -> {
            QrData.Wifi.Authentication.OPEN
        }

        2 -> {
            QrData.Wifi.Authentication.WPA
        }

        else -> {
            QrData.Wifi.Authentication.WEP
        }
    }
}

fun Int?.isGreaterThan(other: Int): Boolean {
    return (this ?: Int.MIN_VALUE) > other
}