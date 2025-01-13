package ir.bahmanghasemi.qrcodeapp.feature_history.domain.use_case

import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity
import ir.bahmanghasemi.qrcodeapp.feature_history.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HistoryUseCase @Inject constructor(
    private val repository: HistoryRepository
) {
    fun getHistories(query: String): Flow<List<QrCodeEntity>> {
        return repository.getHistories(query)
    }

    fun getHistory(id: Int): Flow<QrCodeEntity> {
        return repository.getHistory(id)
    }
}