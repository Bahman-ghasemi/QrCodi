package ir.bahmanghasemi.qrcodeapp.feature_history.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity
import ir.bahmanghasemi.qrcodeapp.feature_history.domain.use_case.HistoryUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val useCase: HistoryUseCase
) : ViewModel() {

    private val _historyUiState = MutableStateFlow(HistoryUiState())
    val historyUiState = _historyUiState.asStateFlow()

    fun getHistories(query: String) {
        viewModelScope.launch {
            try {
                useCase.getHistories(query).collectLatest { result ->
                    _historyUiState.update { it.copy(list = result, errorMessage = null) }
                }
            } catch (ex: Exception) {
                _historyUiState.update { it.copy(errorMessage = ex.localizedMessage) }
            }
        }
    }

    fun getHistory(id: Int) {
        viewModelScope.launch {
            try {
                useCase.getHistory(id).collectLatest { result ->
                    _historyUiState.update { it.copy(pendingHistory = result, errorMessage = null) }
                }
            } catch (ex: Exception) {
                _historyUiState.update { it.copy(errorMessage = ex.localizedMessage) }
            }
        }
    }
}