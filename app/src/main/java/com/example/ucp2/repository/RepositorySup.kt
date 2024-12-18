package com.example.ucp2.repository

import com.example.ucp2.data.Suplier
import kotlinx.coroutines.flow.Flow

interface RepositorySup {
    interface RepositorySup {

        suspend fun insertSupp(suplier: Suplier)
        fun getAllSup(): Flow<List<Suplier>>

        fun getSup(nama: String): Flow<Suplier>

        suspend fun deleteSup(suplier: Suplier)

    }
}