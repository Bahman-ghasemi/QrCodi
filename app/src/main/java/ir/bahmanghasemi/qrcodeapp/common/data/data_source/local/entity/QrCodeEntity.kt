package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.alexzhirkevich.customqrgenerator.QrData

@Entity
data class QrCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: QrData,
    val rawContent: String,
    val extraData: String? = null
)