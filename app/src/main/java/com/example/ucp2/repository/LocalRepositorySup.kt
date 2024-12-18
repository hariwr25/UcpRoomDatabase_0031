package com.example.ucp2.repository

import com.example.ucp2.data.Suplier
import com.example.ucp2.data.dao.SuplierDao
import kotlinx.coroutines.flow.Flow

class LocalRepositorySup(private val suplierDao: SuplierDao) : RepositorySup {

    override suspend fun insertSuplier(suplier: Suplier) {
        suplierDao.insertSuplier(suplier)
    }

    override fun getAllSuplier(): Flow<List<Suplier>> {
        return suplierDao.getAllSuplier()
    }

    override fun getSuplierByName(name: String): Flow<Suplier> {
        return suplierDao.getSuplier(name)
    }
}
