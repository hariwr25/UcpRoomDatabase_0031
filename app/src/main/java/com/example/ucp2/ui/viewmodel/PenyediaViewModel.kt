package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.ucp2.BakulApp
import com.example.ucp2.ui.viewmodel.barang.BarangHomeViewModel
import com.example.ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2.ui.viewmodel.barang.DetailBarangViewModel
import com.example.ucp2.ui.viewmodel.barang.UpdateBarangViewModel
import com.example.ucp2.ui.viewmodel.supplier.SupplierHomeViewModel
import com.example.ucp2.ui.viewmodel.supplier.SupplierViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {

        initializer {
            SupplierViewModel(
                BakulApp().containerApp.repositorySpl
            )
        }
        initializer {
            SupplierHomeViewModel(
                BakulApp().containerApp.repositorySpl
            )
        }
        initializer {
            BarangViewModel(
                BakulApp().containerApp.repositoryBrg,
            )
        }
        initializer {
            BarangHomeViewModel(
                BakulApp().containerApp.repositoryBrg
            )
        }
        initializer {
            DetailBarangViewModel(
                createSavedStateHandle(),
                BakulApp().containerApp.repositoryBrg
            )
        }
        initializer {
            UpdateBarangViewModel(
                createSavedStateHandle(),
                BakulApp().containerApp.repositoryBrg,
            )
        }
    }
}

fun CreationExtras.BakulApp(): BakulApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as BakulApp)