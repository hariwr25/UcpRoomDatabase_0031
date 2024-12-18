package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.Barang
import com.example.ucp2.data.Suplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SuplierDao {
    @Insert
    suspend fun insertSuplier(suplier: Suplier)

    @Query
        ("SELECT * FROM suplier ORDER BY nama ASC")
    fun getAllSuplier(): Flow<List<Suplier>>

    @Query
        ("SELECT * FROM suplier WHERE nama = :nama")
    fun getSuplier(nama: String): Flow<Suplier>

    @Delete
    suspend fun deleteSuplier(mahasiswa: Suplier)

    @Update
    suspend fun updateSuplier(mahasiswa: Suplier)
}