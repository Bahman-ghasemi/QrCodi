package ir.bahmanghasemi.qrcodeapp.common.domain.use_case

import ir.bahmanghasemi.qrcodeapp.feature_history.domain.use_case.HistoryUseCase
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case.QrGeneratorUseCase
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case.QrScanUseCase

data class QrUseCase(
    val qrScanner: QrScanUseCase,
    val qrGenerator: QrGeneratorUseCase,
    val qrHistory: HistoryUseCase
)
