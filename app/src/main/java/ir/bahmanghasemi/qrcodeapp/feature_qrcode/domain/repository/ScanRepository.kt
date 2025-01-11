package ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository

import com.github.alexzhirkevich.customqrgenerator.QrData
import kotlinx.coroutines.flow.Flow

interface ScanRepository {

    fun startScan(): Flow<QrData?>

}