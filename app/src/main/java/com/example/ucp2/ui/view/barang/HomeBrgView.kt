package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.ui.customwidget.LoadingState
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.BarangHomeViewModel
import com.example.ucp2.ui.viewmodel.barang.HomeUIStateBrg
import kotlinx.coroutines.launch

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
@Composable
fun BodyHomeBrgView(
    homeUiState: HomeUIStateBrg,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    when {
        homeUiState.isLoading -> {
            LoadingState()
        }

        homeUiState.isError -> {
            LaunchedEffect(homeUiState.errorMessage) {
                homeUiState.errorMessage?.let { message ->
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(message)
                    }
                }
            }
        }

        homeUiState.listBarang.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Tidak ada data Barang.",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF455A64),
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
        else -> {
            ListBarang(
                listBrg = homeUiState.listBarang,
                onClick = {
                    onClick(it)
                    println(it)
                },
                modifier = modifier
            )
        }
    }
}
@Composable
fun ListBarang(
    listBrg: List<Barang>,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = listBrg,
            itemContent = { brg ->
                CardBarang(
                    brg = brg,
                    onClick = { onClick(brg.id.toString()) }
                )
            }
        )
    }
}

