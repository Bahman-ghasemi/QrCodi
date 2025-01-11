package ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case

import com.github.alexzhirkevich.customqrgenerator.QrData
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.ScanRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QrScanUseCase @Inject constructor(
    private val repository: ScanRepository
) {
    operator fun invoke(): Flow<QrData?> {
        return repository.startScan()
    }
}