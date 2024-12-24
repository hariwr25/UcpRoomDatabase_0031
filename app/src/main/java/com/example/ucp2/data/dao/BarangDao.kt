package com.example.ucp2.data.dao

import androidx.room.*
import com.example.ucp2.data.entity.Barang
import kotlinx.coroutines.flow.Flow

@Dao
interface BarangDao {
    @Insert
    suspend fun insertBarang(barang: Barang)

    @Update
    suspend fun updateBarang(barang: Barang)

    @Delete
    suspend fun deleteBarang(barang: Barang)

    @Query("SELECT * FROM tblBrg ORDER BY namaBarang")
    fun getAllBarang(): Flow<List<Barang>>

    @Query("SELECT * FROM tblBrg WHERE id = :id")
    fun getBarang(id: Int): Flow<Barang>
}