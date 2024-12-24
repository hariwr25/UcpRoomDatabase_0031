package com.example.ucp2.ui.viewmodel.supplier

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Supplier
import com.example.ucp2.repository.supplier.RepositorySup
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class SupplierHomeViewModel(
    private val repositorySpl: RepositorySup
) : ViewModel() {

    val homeUiStateSpl: StateFlow<HomeUIStateSpl> = repositorySpl.getAllSupplier()
        .filterNotNull()
        .map {
            HomeUIStateSpl(
                listSpl = it.toList(),
                isLoading = false
            )
        }
        .onStart {
            emit(HomeUIStateSpl(isLoading = true))
            delay(900)
        }
        .catch {
            emit(
                HomeUIStateSpl(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi Kesalahan"
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUIStateSpl(
                isLoading = true
            )
        )
}

data class HomeUIStateSpl(
    val listSpl: List<Supplier> = listOf(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
)

