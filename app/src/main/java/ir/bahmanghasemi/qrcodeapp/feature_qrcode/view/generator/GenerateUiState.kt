package ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.generator

import android.graphics.drawable.Drawable

data class GenerateUiState(
    val drawable: Drawable? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
