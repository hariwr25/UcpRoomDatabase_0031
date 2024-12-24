package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.ucp2.data.entity.Supplier
import kotlinx.coroutines.flow.Flow

@Dao
interface SupplierDao {

    @Insert
    suspend fun insertSupplier(supplier: Supplier)

    @Query("SELECT * FROM tblSpl ORDER BY id")
    fun getAllSupplier(): Flow<List<Supplier>>

    @Query("SELECT nama FROM tblSpl")
    fun getSupplierNama(): Flow<List<String>>

}