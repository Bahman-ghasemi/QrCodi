package ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.use_case

import android.graphics.drawable.Drawable
import com.github.alexzhirkevich.customqrgenerator.QrData
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.QrGeneratorRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class QrGeneratorUseCase @Inject constructor(private val repository: QrGeneratorRepository) {
    operator fun invoke(content: QrData): Flow<Drawable?> {
        return repository.generate(content)
    }
}