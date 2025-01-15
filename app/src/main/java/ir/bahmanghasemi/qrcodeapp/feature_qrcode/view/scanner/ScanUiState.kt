package ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.scanner

import com.github.alexzhirkevich.customqrgenerator.QrData

data class ScanUiState(
    val result: QrData? = null,
    val isFlashOn: Boolean = false,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
)
