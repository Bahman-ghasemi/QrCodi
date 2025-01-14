package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TypeConverter {
    @TypeConverter
    fun fromMap(map: Map<String, String>?): String? {
        return if (map == null) null else Gson().toJson(map)
    }

    @TypeConverter
    fun toMap(json: String?): Map<String, String>? {
        return if (json == null) null else Gson().fromJson(json, object : TypeToken<Map<String, String>>() {}.type)
    }
}