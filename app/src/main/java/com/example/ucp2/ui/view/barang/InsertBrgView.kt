package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.R
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.BarangEvent
import com.example.ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2.ui.viewmodel.barang.brgUIState
import kotlinx.coroutines.launch

@Composable
fun InsertBrgView(
    onBackArrow: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: BarangViewModel = viewModel(factory = PenyediaViewModel.Factory)
) {
    val uiState = viewModel.uiBrgState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val title = "Tambah Data Barang"

    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                viewModel.resetSnackBarBrgMessage()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = title,
                onBackClick = onBackArrow,
                actionIcon = R.drawable.box
            )
        },
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            InsertBodyBrg(
                uiState = uiState,
                formTitle = "Form $title",
                onValueChange = { updatedEvent -> viewModel.updateBrgState(updatedEvent) },
                onClick = {
                    val isSaved = viewModel.saveDataBrg()
                    if (isSaved) {
                        onNavigate()
                    }
                }
            )
        }
    }
}
@Composable
fun InsertBodyBrg(
    modifier: Modifier = Modifier,
    formTitle: String,
    onValueChange: (BarangEvent) -> Unit,
    uiState: brgUIState,
    onClick: suspend () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF3F4F6),
                        Color(0xFFD7E8FF)
                    )
                )
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formTitle,
            color = Color(0xFF6200EA),
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Start)
        )

        FormBarang(
            barangEvent = uiState.barangEvent,
            onValueChange = onValueChange,
            errorBrgState = uiState.isEntryBrgValid,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                coroutineScope.launch {
                    onClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EA),
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Simpan",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

