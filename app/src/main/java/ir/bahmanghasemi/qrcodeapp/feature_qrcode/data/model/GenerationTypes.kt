package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model

import androidx.compose.ui.graphics.Color

data class QrGeneratorItem(
    val text: String,
    val icon: Int,
    val tintColor: Color,
    val backgroundColor: Color,
    val onClick: (QrAction) -> Unit,
)
