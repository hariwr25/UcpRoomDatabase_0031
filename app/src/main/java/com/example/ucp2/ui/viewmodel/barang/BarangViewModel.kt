package com.example.ucp2.ui.viewmodel.barang

import com.example.ucp2.data.entity.Barang


data class brgUIState(
    val barangEvent: BarangEvent = BarangEvent(),
    val isEntryBrgValid: FormErrorBrgState = FormErrorBrgState(),
    val snackBarMessage: String? = null
)

data class FormErrorBrgState(
    val id: String? = null,
    val namaBarang: String? = null,
    val deskripsi: String? = null,
    val harga: String? = null,
    val stok: String? = null,
    val namaSupplier: String? = null
) {
    fun isBrgValid(): Boolean {
        return id == null && namaBarang == null && harga == null &&
                deskripsi == null && stok == null && namaSupplier == null
    }
}

fun BarangEvent.toBarangEntity(): Barang = Barang(
    id = id.toIntOrNull() ?: 0,
    namaBarang = namaBarang,
    deskripsi = deskripsi,
    harga = harga.toIntOrNull() ?: 0,
    stok = stok.toIntOrNull() ?: 0,
    namaSupplier = namaSupplier
)

data class BarangEvent(
    val id: String = "",
    val namaBarang: String = "",
    val deskripsi: String = "",
    val harga: String = "",
    val stok: String = "",
    var namaSupplier: String = ""
)