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
    suspend fun addNewStock(stock: Stock)

    @Delete
    suspend fun deleteStock(vararg item: Stock)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStock(stock: Stock)

    @Query("SELECT * from stocks_table ORDER BY companyName ASC")
    fun getAllStocks(): LiveData<List<Stock>>

    @Query("SELECT * from stocks_table WHERE companyName LIKE:name")
    suspend fun getStockByName(name: String): Stock

    @Query("SELECT * from stocks_table WHERE stockTicker LIKE:ticker")
    suspend fun getStockByTicker(ticker: String): Stock

    @Query("DELETE from stocks_table")
    suspend fun deleteAll()

    @Query("UPDATE stocks_table SET currPrice = :newPrice WHERE companyName = :stockName")
    suspend fun updateCurrentPrice(stockName: String, newPrice: Double)

    @Query("SELECT SUM(ownedAmount) FROM stocks_table WHERE companyName LIKE:stockName")
    fun getTotalAmountForStockFlow(stockName: String): LiveData<Int>  // TODO: delete me!!!

    @Query("SELECT * FROM stocks_table WHERE isFavorite = 1 ORDER BY companyName ASC")
    fun getFavorites(): LiveData<List<Stock>>

}

