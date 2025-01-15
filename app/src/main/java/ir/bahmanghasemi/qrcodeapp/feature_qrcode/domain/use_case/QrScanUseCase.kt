package ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case

import android.net.Uri
import com.github.alexzhirkevich.customqrgenerator.QrData
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QrScanUseCase @Inject constructor(
    private val repository: ScanRepository
) {
    suspend fun cameraScan(): Flow<QrData?> {
        return repository.startCameraScan()
    }

    suspend fun photoScan(uri:Uri): Flow<QrData?> {
        return repository.startPhotoScan(uri)
    }
}