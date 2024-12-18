package com.example.ucp2.repository

import com.example.ucp2.data.Barang
import com.example.ucp2.data.dao.BarangDao
import kotlinx.coroutines.flow.Flow

class LocalRepositoryBrg(
    private val barangDao: BarangDao
) : RepositoryBrg {

}