package com.example.ucp2.repository

import com.example.ucp2.data.Barang
import kotlinx.coroutines.flow.Flow

interface RepositoryBrg {
    interface RepositoryBrg {

        suspend fun insertBrg(barang: Barang)
        fun getAllBrg(): Flow<List<Barang>>

        fun getBrg(nim: String): Flow<Barang>

        suspend fun deleteBrg(barang: Barang)

        suspend fun updateBrg(barang: Barang)
    }
}