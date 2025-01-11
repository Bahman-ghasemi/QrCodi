package ir.bahmanghasemi.qrcodeapp.feature_qrcode.data.repository

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.github.alexzhirkevich.customqrgenerator.QrData
import com.github.alexzhirkevich.customqrgenerator.vector.QrCodeDrawable
import com.github.alexzhirkevich.customqrgenerator.vector.QrVectorOptions
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorBallShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorFrameShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogo
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoPadding
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorLogoShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorPixelShape
import com.github.alexzhirkevich.customqrgenerator.vector.style.QrVectorShapes
import ir.bahmanghasemi.qrcodeapp.R
import ir.bahmanghasemi.qrcodeapp.feature_qrcode.domain.repository.QrGeneratorRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class QrGeneratorRepositoryImpl @Inject constructor(
    private val context: Context
) : QrGeneratorRepository {
    override fun generate(content: QrData): Flow<Drawable?> {
        return callbackFlow {
            val result = QrCodeDrawable(content, option(context))
            send(result)
            awaitClose {
            }
        }
    }
}

private fun option(context: Context): QrVectorOptions {
    return QrVectorOptions.Builder()
        .setPadding(.3f)
        .setLogo(
            QrVectorLogo(
                drawable = ContextCompat.getDrawable(context, R.drawable.phone_fill),
                size = .25f,
                padding = QrVectorLogoPadding.Natural(.2f),
                shape = QrVectorLogoShape.Circle
            )
        )
        /*.setBackground(
            QrVectorBackground(drawable = ContextCompat.getDrawable(context, R.drawable.frame))
        )*/
        /*.setColors(
            QrVectorColors(
                dark = QrVectorColor.Solid(0xff345288.toInt()),
                ball = QrVectorColor.Solid(ContextCompat.getColor(context, R.color.teal_200)),
                frame = QrVectorColor.LinearGradient(
                    colors = listOf(
                        0f to Color.RED,
                        1f to Color.BLUE,
                    ),
                    orientation = QrVectorColor.LinearGradient
                        .Orientation.LeftDiagonal
                )
            )
        )*/
        .setShapes(
            QrVectorShapes(
                darkPixel = QrVectorPixelShape
                    .RoundCorners(.5f),
                ball = QrVectorBallShape
                    .RoundCorners(.25f),
                frame = QrVectorFrameShape
                    .RoundCorners(.25f),
            )
        )
        .build()
}