package com.example.ucp2.ui.viewmodel.supplier

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.ucp2.data.entity.Supplier
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

    suspend fun saveDataSupplier(): Boolean {
        val currentSplEvent = uiSplState.supplierEvent

        return if (validateSplFields()) {
            try {
                repositorySup.insertSupplier(currentSplEvent.toSupplierEntity())
                uiSplState = uiSplState.copy(
                    snackBarMessage = "Data berhasil disimpan ke dalam sistem.",
                    supplierEvent = SupplierEvent(),
                    isEntrySplValid = FormErrorSplState()
                )
                true
            } catch (e: Exception) {
                uiSplState =
                    uiSplState.copy(snackBarMessage = "Terjadi kesalahan saat menyimpan data supplier.")
                false
            }
        } else {
            uiSplState =
                uiSplState.copy(snackBarMessage = "Data input tidak valid. Mohon periksa kembali.")
            false
        }
    }

    fun resetSnackBarSplMessage() {
        uiSplState = uiSplState.copy(snackBarMessage = null)
    }
}

data class splUIState(
    val supplierEvent: SupplierEvent = SupplierEvent(),
    val isEntrySplValid: FormErrorSplState = FormErrorSplState(),
    val snackBarMessage: String? = null
)

data class FormErrorSplState(
    val nama: String? = null,
    val kontak: String? = null,
    val alamat: String? = null
) {
    fun isSplValid(): Boolean {
        return nama == null &&
                kontak == null && alamat == null
    }
}

fun SupplierEvent.toSupplierEntity(): Supplier = Supplier(
    id = id,
    nama = nama,
    kontak = kontak,
    alamat = alamat
)

data class SupplierEvent(
    val id: Int = 0,
    val nama: String = "",
    val kontak: String = "",
    val alamat: String = ""
)
