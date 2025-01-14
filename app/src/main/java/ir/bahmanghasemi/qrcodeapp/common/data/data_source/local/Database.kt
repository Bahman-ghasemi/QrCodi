package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.converter.QrTypeConverter
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.converter.TypeConverter
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.dao.HistoryDao
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity

@Database(entities = [QrCodeEntity::class], version = 1)
@TypeConverters(TypeConverter::class, QrTypeConverter::class)
abstract class Database : RoomDatabase() {
    abstract val historyDao: HistoryDao
}