package ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.entity.QrCodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(qrCode: QrCodeEntity)

    @Delete
    suspend fun delete(qrCode: QrCodeEntity)

    @Query("SELECT * FROM QrCodeEntity WHERE rawContent LIKE '%' || :query || '%'")
    fun getHistories(query:String): Flow<List<QrCodeEntity>>

    @Query("SELECT * FROM QrCodeEntity WHERE id = :id")
    fun getHistory(id:Int):Flow<QrCodeEntity>

}