package com.example.ucp2.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.supplier.SupplierHomeViewModel

object NamaSupplier {
    @Composable
    fun options(
        supplierHomeViewModel: SupplierHomeViewModel = viewModel(factory = PenyediaViewModel.Factory)
    ): List<String> {
        val dataNama by supplierHomeViewModel.homeUiStateSpl.collectAsState()
        val namaSupplier = dataNama.listSpl.map { it.nama }
        return namaSupplier
    }
}
