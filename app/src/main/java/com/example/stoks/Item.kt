package com.example.stoks

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

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
    var currPrice: Double
)
{
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

// This is a singleton
//object ItemManager {
//    val items: MutableList<Item> = mutableListOf()
//
//    fun add (item: Item) {
//        items.add(item)
//    }
//
//    fun remove(index: Int) {
//        items.removeAt(index)
//    }
//
//
//
//}