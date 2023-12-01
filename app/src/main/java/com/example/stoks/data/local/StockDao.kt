package com.example.stoks.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stoks.data.model.Stock


@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addItem(stock: Stock)

    @Delete
    suspend fun deleteItem(vararg item: Stock)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateItem(stock: Stock)

    @Query("SELECT * from stocks_table ORDER BY stockName ASC")
    fun getItems(): LiveData<List<Stock>>

    @Query("SELECT * from stocks_table WHERE stockName LIKE:title")
    suspend fun getItem(title: String): Stock

    @Query("DELETE from stocks_table")
    suspend fun deleteAll()

    @Query("UPDATE stocks_table SET currPrice = :newPrice WHERE stockName = :stockName")
    suspend fun updateCurrentPrice(stockName: String, newPrice: Double)

    @Query("SELECT SUM(stockAmount) FROM stocks_table WHERE stockName LIKE:stockName")
    fun getTotalAmountForStockFlow(stockName: String): LiveData<Int>

    @Query("SELECT * FROM stocks_table WHERE isFavorite = 1 ORDER BY stockName ASC")
    fun getFavorites(): LiveData<List<Stock>>

}
