package com.example.ucp2.data.dao

import androidx.room.*
import com.example.ucp2.data.Barang
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {

    // Insert Barang
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBarang(barang: Barang)

    // Update Barang
    @Update
    suspend fun updateBarang(barang: Barang)

    // Delete Barang
    @Delete
    suspend fun deleteBarang(barang: Barang)

    // Ambil Semua Barang
    @Query("SELECT * FROM Barang ORDER BY nama ASC")
    fun getAllBarang(): Flow<List<Barang>>

    // Ambil Barang berdasarkan Nama
    @Query("SELECT * FROM Barang WHERE nama = :nama")
    fun getBarangByName(nama: String): Flow<Barang>

    // Ambil Barang beserta Nama Suplier dengan JOIN
    @Query("""
        SELECT Barang.id, Barang.nama, Barang.deskripsi, Barang.harga, Barang.stok, Suplier.nama AS namaSuplier
        FROM Barang 
        INNER JOIN Suplier ON Barang.nama = Suplier.id
        ORDER BY Barang.nama ASC
    """)
    fun getBarangWithSuplier(): Flow<List<Barang>>
}
