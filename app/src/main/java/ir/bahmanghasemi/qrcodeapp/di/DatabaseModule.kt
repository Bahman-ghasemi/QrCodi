package ir.bahmanghasemi.qrcodeapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.Database
import ir.bahmanghasemi.qrcodeapp.common.data.data_source.local.dao.HistoryDao
import ir.bahmanghasemi.qrcodeapp.common.data.util.Const

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context = context,
            Database::class.java,
            name = Const.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideHistoryDao(database: Database):HistoryDao{
        return database.historyDao
    }
}