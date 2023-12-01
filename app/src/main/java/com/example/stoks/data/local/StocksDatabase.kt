package com.example.stoks.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.stoks.data.model.Stock


@Database(entities = arrayOf(Stock::class), version = 1, exportSchema = false)

abstract class StocksDatabase : RoomDatabase() {

    abstract fun itemsDao(): StockDao

    companion object {
        @Volatile
        private var instance: StocksDatabase? = null

        fun getDatabase(context: Context) = instance ?: synchronized(StocksDatabase::class.java)
        {
            Room.databaseBuilder(
                context.applicationContext,
                StocksDatabase::class.java, "stocks_database"
            ).fallbackToDestructiveMigration().build().also { instance = it }
        }

    }

}