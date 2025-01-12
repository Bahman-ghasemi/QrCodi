package ir.bahmanghasemi.qrcodeapp.common.view.navigation

import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.ScanMode
import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
data class ScanRoute(val scanMode: ScanMode)

@Serializable
object GenerationRoute

@Serializable
object HistoryRoute

@Serializable
object SettingRoute