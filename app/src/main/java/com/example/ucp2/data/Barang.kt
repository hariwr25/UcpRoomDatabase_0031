package com.example.ucp2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Barang")
data class Barang(
    @PrimaryKey
    val id: String,
    val nama: String,
    val deskripsi: String,
    val harga: String,
    val stok: String,
    val nama_suplier: String,
)
