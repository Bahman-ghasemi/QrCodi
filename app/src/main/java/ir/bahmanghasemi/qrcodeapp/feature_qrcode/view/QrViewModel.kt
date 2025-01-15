package ir.bahmanghasemi.qrcodeapp.feature_qrcode.view

import android.app.Application
import android.content.Context
import android.hardware.camera2.CameraManager
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.alexzhirkevich.customqrgenerator.QrData
import dagger.hilt.android.lifecycle.HiltViewModel
import ir.bahmanghasemi.qrcodeapp.common.domain.use_case.QrUseCase
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.generator.GenerateUiState
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.view.scanner.ScanUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QrViewModel @Inject constructor(
    private val qrUseCase: QrUseCase,
    private val application: Application
) : AndroidViewModel(application) {

    private val _scanUiState = MutableStateFlow(ScanUiState())
    val scanUiState = _scanUiState.asStateFlow()

    private val _generateUiState = MutableStateFlow(GenerateUiState())
    val generateUiState = _generateUiState.asStateFlow()

    fun startCameraScan() {
        _scanUiState.update { ScanUiState(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            qrUseCase.qrScanUseCase.cameraScan().collectLatest { data ->
                try {
                    _scanUiState.update { ScanUiState(result = data) }
                } catch (ex: Exception) {
                    _scanUiState.update { ScanUiState(errorMessage = ex.localizedMessage) }
                }
            }
        }
    }

    fun startPhotoScan(uri: Uri) {
        _scanUiState.update { ScanUiState(isLoading = true) }
        viewModelScope.launch(Dispatchers.IO) {
            qrUseCase.qrScanUseCase.photoScan(uri).collectLatest { data ->
                try {
                    _scanUiState.update { ScanUiState(result = data) }
                } catch (ex: Exception) {
                    _scanUiState.update { ScanUiState(errorMessage = ex.localizedMessage) }
                }
            }
        }
    }

    fun toggleFlashLight() {
        val cameraManager = application.applicationContext.getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            val cameraId = cameraManager.cameraIdList.firstOrNull { id ->
                cameraManager.getCameraCharacteristics(id)
                    .get(android.hardware.camera2.CameraCharacteristics.FLASH_INFO_AVAILABLE) == true
            }

            cameraId?.let {
                _scanUiState.update { ScanUiState(isFlashOn = !it.isFlashOn) }
                cameraManager.setTorchMode(cameraId, _scanUiState.value.isFlashOn)
            }

        } catch (e: Exception) {
            e.printStackTrace()
            _scanUiState.update { ScanUiState(errorMessage = e.localizedMessage) }
        }
    }

    fun generateQrCode(content: QrData) {
        _generateUiState.update { GenerateUiState(isLoading = true) }
        viewModelScope.launch {
            try {
                qrUseCase.qrGeneratorUseCase.invoke(content).collectLatest { drawable ->
                    _generateUiState.update { GenerateUiState(drawable = drawable) }
                }
            } catch (ex: Exception) {
                _generateUiState.update { GenerateUiState(errorMessage = ex.localizedMessage) }
            }
        }
    }

}