package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.dao.HistoryDao
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity

@Database(entities = [QrCodeEntity::class], version = 1)
sealed class Database : RoomDatabase() {
    abstract val historyDao: HistoryDao
}