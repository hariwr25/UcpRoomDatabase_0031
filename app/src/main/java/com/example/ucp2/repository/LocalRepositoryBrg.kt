package com.example.ucp2.repository

import com.example.ucp2.data.Barang
import com.example.ucp2.data.dao.BarangDao
import kotlinx.coroutines.flow.Flow

class LocalRepositoryBrg(private val barangDao: BarangDao) {

    suspend fun insertBarang(barang: Barang) {
        barangDao.insertBarang(barang)
    }

    suspend fun updateBarang(barang: Barang) {
        barangDao.updateBarang(barang)
    }

    suspend fun deleteBarang(barang: Barang) {
        barangDao.deleteBarang(barang)
    }

    fun getAllBarang(): Flow<List<Barang>> {
        return barangDao.getAllBarang()
    }

    fun getBarangByNama(nama: String): Flow<Barang> {
        return barangDao.getBarangByName(nama)
    }

    fun getBarangWithSupplier(): Flow<List<Barang>> {
        return barangDao.getBarangWithSuplier()
    }
}
