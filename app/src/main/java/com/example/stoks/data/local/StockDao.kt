package com.example.stoks.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.stoks.data.model.StockLocalModel


@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNewStock(stock: StockLocalModel)

    @Delete
    suspend fun deleteStock(vararg item: StockLocalModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateStockData(stock: StockLocalModel)

    @Query("SELECT * from stocks_table ORDER BY companyName ASC")
    fun getAllStocks(): LiveData<List<StockLocalModel>>

    @Query("SELECT * from stocks_table WHERE companyName LIKE:name")
    suspend fun getStockByName(name: String): StockLocalModel

    @Query("SELECT * from stocks_table WHERE stockTicker LIKE:ticker")
    suspend fun getStockByTicker(ticker: String): StockLocalModel

    @Query("DELETE from stocks_table")
    suspend fun deleteAllStocks()

    @Query("UPDATE stocks_table SET currPrice = :newPrice WHERE companyName = :stockName")
    suspend fun updateCurrentPrice(stockName: String, newPrice: Double)

    @Query("SELECT SUM(ownedAmount) FROM stocks_table WHERE companyName LIKE:stockName")
    fun getTotalAmountForStockFlow(stockName: String): LiveData<Int>  // TODO: delete me!!!

    @Query("SELECT * FROM stocks_table WHERE isFavorite = 1 ORDER BY companyName ASC")
    fun getFavoriteStocks(): LiveData<List<StockLocalModel>>

}

