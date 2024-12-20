package com.example.ucp2.repository

import com.example.ucp2.data.Barang
import kotlinx.coroutines.flow.Flow

interface RepositoryBrg {
    interface RepositoryBrg {

        suspend fun insertBrg(barang: Barang)
        suspend fun updateBrg(barang: Barang)
        suspend fun deleteBrg(barang: Barang)
        fun getAllBrg(): Flow<List<Barang>>
        fun getBarangByName(name: String): Flow<Barang>
        fun getBarangWithSupplier(): Flow<List<Barang>>


    }
}