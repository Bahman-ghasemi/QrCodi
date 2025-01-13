package ir.bahmanghasemi.qrcodeapp.feature_history.view

import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity

data class HistoryUiState(
    val list: List<QrCodeEntity> = emptyList(),
    val pendingHistory: QrCodeEntity? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)
