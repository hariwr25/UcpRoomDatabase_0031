package com.example.ucp2.repository

import com.example.ucp2.data.Suplier
import kotlinx.coroutines.flow.Flow

interface RepositorySup {
    suspend fun insertSuplier(suplier: Suplier)
    fun getAllSuplier(): Flow<List<Suplier>>
    fun getSuplierByName(name: String): Flow<Suplier>
}
