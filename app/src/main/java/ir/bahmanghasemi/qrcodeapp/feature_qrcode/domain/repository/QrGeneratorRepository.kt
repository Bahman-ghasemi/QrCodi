package ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository

import android.graphics.drawable.Drawable
import com.github.alexzhirkevich.customqrgenerator.QrData
import kotlinx.coroutines.flow.Flow

interface QrGeneratorRepository {
    fun generate(content: QrData): Flow<Drawable?>
}