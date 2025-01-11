package ir.bahmanghasemi.qrcodeapp.feature_qrcode.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alexzhirkevich.customqrgenerator.QrData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.qrcodeapp.common.domain.use_case.QrUseCase
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.generator.GenerateUiState
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.scanner.ScanUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val qrUseCase: QrUseCase
) : ViewModel() {

    private val _scanUiState = MutableStateFlow(ScanUiState())
    val scanUiState = _scanUiState.asStateFlow()

    private val _generateUiState = MutableStateFlow(GenerateUiState())
    val generateUiState = _generateUiState.asStateFlow()

    fun startScan() {
        _scanUiState.update { ScanUiState(isLoading = true) }
        viewModelScope.launch {
            qrUseCase.qrScanner.invoke().collectLatest { data ->
                try {
                    _scanUiState.update { ScanUiState(result = data) }
                } catch (ex: Exception) {
                    _scanUiState.update { ScanUiState(errorMessage = ex.localizedMessage) }
                }
            }
        }
    }

    fun generateQrCode(content: QrData) {
        _generateUiState.update { GenerateUiState(isLoading = true) }
        viewModelScope.launch {
            try {
                qrUseCase.qrGenerator.invoke(content).collectLatest { drawable ->
                    _generateUiState.update { GenerateUiState(drawable = drawable) }
                }
            } catch (ex: Exception) {
                _generateUiState.update { GenerateUiState(errorMessage = ex.localizedMessage) }
            }
        }
    }

}