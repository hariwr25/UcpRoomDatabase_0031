package com.example.ucp2.repository.supplier

import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.data.dao.SupplierDao
import kotlinx.coroutines.flow.Flow

class LocalRepositorySup(
    private val supplierDao: SupplierDao
) : RepositorySup {

    override suspend fun insertSupplier(supplier: Supplier) {
        supplierDao.insertSupplier(supplier)
    }

    override fun getAllSupplier(): Flow<List<Supplier>>  =
        supplierDao.getAllSupplier()

    override fun getSupplierNama(): Flow<List<String>> =
        supplierDao.getSupplierNama()
}
