package com.example.ucp2.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.repository.barang.RepositoryBrg
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateBarangViewModel(
    savedStateHandle: SavedStateHandle,
    private val repositoryBrg: RepositoryBrg
) : ViewModel() {

    var updateBrgUiState by mutableStateOf(brgUIState())
        private set

    private val _idBrg: String = checkNotNull(savedStateHandle[DestinasiUpdateBrg.idBrg])

    init {
        viewModelScope.launch {
            updateBrgUiState = repositoryBrg.getBarang(_idBrg.toInt())
                .filterNotNull()
                .first()
                .toUIStateBrg()
        }
    }

    fun UpdateStateBrg(barangEvent: BarangEvent) {
        updateBrgUiState = updateBrgUiState.copy(
            barangEvent = barangEvent
        )
    }

    fun validateBrgFields(): Boolean {
        val event = updateBrgUiState.barangEvent
        val errorBrgState = FormErrorBrgState(
            namaBarang = if (event.namaBarang.isNotEmpty()) null else "Nama barang harus diisi",
            deskripsi = if (event.deskripsi.isNotEmpty()) null else "Deskripsi tidak boleh kosong",
            harga = if (event.harga.isNotEmpty()) null else "Harga harus diisi",
            stok = if (event.stok.isNotEmpty()) null else "Stok tidak boleh kosong",
            namaSupplier = if (event.namaSupplier.isNotEmpty()) null else "Nama supplier harus diisi"
        )

        updateBrgUiState = updateBrgUiState.copy(isEntryBrgValid = errorBrgState)
        return errorBrgState.isBrgValid()
    }

    suspend fun updateDataBrg(): Boolean {
        val currentBrgEvent = updateBrgUiState.barangEvent

        return if (validateBrgFields()) {
            try {
                repositoryBrg.updateBarang(currentBrgEvent.toBarangEntity())
                updateBrgUiState = updateBrgUiState.copy(
                    snackBarMessage = "Data berhasil diperbarui",
                    barangEvent = BarangEvent(),
                    isEntryBrgValid = FormErrorBrgState()
                )
                true
            } catch (e: Exception) {
                updateBrgUiState =
                    updateBrgUiState.copy(snackBarMessage = "Gagal memperbarui data barang")
                false
            }
        } else {
            updateBrgUiState =
                updateBrgUiState.copy(snackBarMessage = "Data tidak valid. Harap periksa kembali.")
            false
        }
    }

    fun resetSnackBarMessage() {
        updateBrgUiState = updateBrgUiState.copy(snackBarMessage = null)
    }
}

fun Barang.toUIStateBrg(): brgUIState = brgUIState(
    barangEvent = this.toDetailBrglUiEvent()
)

