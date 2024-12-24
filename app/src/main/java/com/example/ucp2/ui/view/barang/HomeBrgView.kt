package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.BarangHomeViewModel

@Composable
fun HomeBrgView(
    viewModel: BarangHomeViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onAddBrgClick: () -> Unit = { },
    onDetailBrgClick: (String) -> Unit = { },
    onBackArrow: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = "Data Barang",
                onBackClick = onBackArrow,
                actionIcon = R.drawable.item
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddBrgClick,
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF6200EA),
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Barang",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        val homeBrgUiState by viewModel.homeUiStateBrg.collectAsState()

        BodyHomeBrgView(
            homeUiState = homeBrgUiState,
            onClick = {
                onDetailBrgClick(it)
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
