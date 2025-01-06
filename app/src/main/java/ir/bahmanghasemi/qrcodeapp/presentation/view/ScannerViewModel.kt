package ir.bahmanghasemi.qrcodeapp.presentation.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.qrcodeapp.domain.repository.ScannerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repo: ScannerRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScannerUiState())
    val uiState = _uiState.asStateFlow()

    fun statScan() {
        viewModelScope.launch {
            repo.startScan().collectLatest { data ->
                data?.let {
                    _uiState.update { it.copy(result = data) }
                }
            }
        }
    }

}