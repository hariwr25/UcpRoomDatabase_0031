package com.example.ucp2.ui.viewmodel.barang

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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