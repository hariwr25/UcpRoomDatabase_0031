package com.example.ucp2.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Suplier(
    @PrimaryKey
    val id: String,
    val nama: String,
    val kontak: String,
    val alamat: String,
)
