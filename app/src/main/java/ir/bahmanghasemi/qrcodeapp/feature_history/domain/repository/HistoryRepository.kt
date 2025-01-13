package ir.bahmanghasemi.qrcodeapp.feature_history.domain.repository

import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getHistories(query:String): Flow<List<QrCodeEntity>>
    fun getHistory(id:Int): Flow<QrCodeEntity>
}