package com.example.stoks.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stoks.data.utils.UriTypeConverter

@Entity(tableName = "stocks_table")
@TypeConverters(UriTypeConverter::class)
data class StockLocalModel(
    @ColumnInfo(name = "companyName")
    val companyName: String,

    @ColumnInfo(name = "stockTicker")
    val stockTicker: String,

    @ColumnInfo(name = "boughtPrice")
    val boughtPrice: Double,

    @ColumnInfo(name = "currPrice")
    var currentPrice: Double,

    @ColumnInfo(name = "ownedAmount")
    var ownedAmount: Long,

    @ColumnInfo(name = "companyImage")
    val companyImage: Uri?,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

