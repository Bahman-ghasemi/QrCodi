package ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository

import android.net.Uri
import com.github.alexzhirkevich.customqrgenerator.QrData
import kotlinx.coroutines.flow.Flow

interface ScanRepository {

    suspend fun startCameraScan(): Flow<QrData?>

    suspend fun startPhotoScan(uri:Uri): Flow<QrData?>
}