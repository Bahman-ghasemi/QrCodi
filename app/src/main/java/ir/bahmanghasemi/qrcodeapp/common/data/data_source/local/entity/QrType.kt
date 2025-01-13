package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity

import androidx.compose.ui.graphics.Color
import ir.bahmanghasemi.qrcodeapp.R
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.model.QrAction

data class QrType(
    val type: QrAction,
    val icon: Int,
    val color: Color
) {
    companion object {
        fun getTypes(): List<QrType> = listOf(
            QrType(type = QrAction.Mail, icon = R.drawable.mail, color = Color(0xffff3737)),
            QrType(type = QrAction.Phone, icon = R.drawable.phone, color = Color(0xff5ec1f3)),
            QrType(type = QrAction.Message, icon = R.drawable.messaging, color = Color(0xff50db63)),
            QrType(type = QrAction.Text, icon = R.drawable.text, color = Color(0xffbcbcbd)),
            QrType(type = QrAction.Url, icon = R.drawable.url, color = Color(0xffff9800)),
            QrType(type = QrAction.Card, icon = R.drawable.card, color = Color(0xffff4abe)),
            QrType(type = QrAction.FaceBook, icon = R.drawable.facebook, color = Color(0xff3f64b3)),
            QrType(type = QrAction.Instagram, icon = R.drawable.insta, color = Color(0xffe3266d)),
            QrType(type = QrAction.Whatsapp, icon = R.drawable.whatsapp, color = Color(0xff49c170)),
            QrType(type = QrAction.Viber, icon = R.drawable.phone_fill, color = Color(0xff764bda)),
        )
    }

    fun getType(type: QrAction): QrType? {
        return getTypes().find { it.type == type }
    }
}
