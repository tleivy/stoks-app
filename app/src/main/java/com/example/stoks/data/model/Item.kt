package com.example.stoks.data.model

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stoks.data.utils.UriTypeConverter

@Entity(tableName = "items_table")
@TypeConverters(UriTypeConverter::class)
data class Item(
    @ColumnInfo(name = "stockName")
    val stockName: String,

    @ColumnInfo(name = "stockSymbol")
    val stockSymbol: String,

    @ColumnInfo(name = "stockPrice")
    val stockPrice: Double,

    @ColumnInfo(name = "stockAmount")
    val stockAmount: Long,

    @ColumnInfo(name = "stockImage")
    val stockImage: Uri?,

    @ColumnInfo(name = "currPrice")
    var currPrice: Double,

    @ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

