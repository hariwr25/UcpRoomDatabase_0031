package com.example.ucp2.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "Barang",
    foreignKeys = [ForeignKey(
        entity = Suplier::class,          // Tabel Referensi
        parentColumns = ["id"],           // Kolom di tabel Suplier
        childColumns = ["suplier_id"],    // Kolom di tabel Barang
        onDelete = ForeignKey.CASCADE     // Hapus data Barang jika Suplier dihapus
    )]
)
data class Barang(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID Barang
    val nama: String,
    val deskripsi: String,
    val harga: Double,
    val stok: Int,
    val suplier_id: Int // Kolom Foreign Key
)
