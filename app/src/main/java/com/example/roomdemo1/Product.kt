package com.example.roomdemo1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "productId")
    var id: Int = 0,

    @ColumnInfo(name = "productName")
    var productName: String,

    @ColumnInfo(name = "quantity")
    var quantity: Int
)