package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrType

class QrTypeConverter {

    @TypeConverter
    fun fromQrType(qrType: QrType): String {
        return Gson().toJson(qrType)
    }

    @TypeConverter
    fun toQrType(json: String): QrType {
        val type = object : TypeToken<QrType>() {}.type
        return Gson().fromJson(json, type)
    }
}