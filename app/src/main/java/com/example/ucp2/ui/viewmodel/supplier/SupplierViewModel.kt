package com.example.ucp2.ui.viewmodel.supplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2.repository.supplier.RepositorySup

class SupplierViewModel(private val repositorySup: RepositorySup) : ViewModel() {
    var uiSplState by mutableStateOf(splUIState())

    fun updateSplState(supplierEvent: SupplierEvent) {
        uiSplState = uiSplState.copy(supplierEvent = supplierEvent)
    }

    private fun validateSplFields(): Boolean {
        val event = uiSplState.supplierEvent
        val errorSplState = FormErrorSplState(
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong. Silakan diisi.",
            kontak = if (event.kontak.isNotEmpty()) null else "Kontak harus diisi.",
            alamat = if (event.alamat.isNotEmpty()) null else "Alamat wajib diisi."
        )

        uiSplState = uiSplState.copy(isEntrySplValid = errorSplState)
        return errorSplState.isSplValid()
    }
}
