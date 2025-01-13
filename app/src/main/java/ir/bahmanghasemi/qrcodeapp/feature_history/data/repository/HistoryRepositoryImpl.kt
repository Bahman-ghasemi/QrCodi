package ir.bahmanghasemi.qrcodeapp.feature_history.data.repository

import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.dao.HistoryDao
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity
import ir.bahmanghasemi.qrcodeapp.feature_history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryRepositoryImpl @Inject constructor(
    private val dao: HistoryDao
) : HistoryRepository {

    override fun getHistories(query: String): Flow<List<QrCodeEntity>> {
        return dao.getHistories(query)
    }

    override fun getHistory(id: Int): Flow<QrCodeEntity> {
        return dao.getHistory(id)
    }
}