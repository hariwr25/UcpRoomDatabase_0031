package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.data.NamaSupplier
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.BarangEvent
import com.example.ucp2.ui.viewmodel.barang.BarangViewModel
import com.example.ucp2.ui.viewmodel.barang.FormErrorBrgState
import com.example.ucp2.ui.viewmodel.barang.brgUIState
import com.example.ucp22.R
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
                actionIcon = android.R.drawable.ic_menu_manage, // Menggunakan ikon bawaan Android
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
@Composable
fun FormBarang(
    modifier: Modifier = Modifier,
    barangEvent: BarangEvent = BarangEvent(),
    onValueChange: (BarangEvent) -> Unit = { },
    errorBrgState: FormErrorBrgState = FormErrorBrgState()
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.namaBarang,
            onValueChange = {
                onValueChange(barangEvent.copy(namaBarang = it))
            },
            label = { Text("Nama Barang") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.item),
                    contentDescription = "Nama Barang Icon",
                    tint = Color(0xFF6200EA)
                )
            },
            placeholder = { Text("Masukkan Nama Barang") },
            isError = errorBrgState.namaBarang != null,
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EA),
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red
            )
        )
        if (errorBrgState.namaBarang != null) {
            Text(
                text = errorBrgState.namaBarang,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.deskripsi,
            onValueChange = {
                onValueChange(barangEvent.copy(deskripsi = it))
            },
            label = { Text("Deskripsi") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Deskripsi Icon",
                    tint = Color(0xFF6200EA)
                )
            },
            placeholder = { Text("Masukkan Deskripsi Barang") },
            isError = errorBrgState.deskripsi != null,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EA),
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red
            )
        )
        if (errorBrgState.deskripsi != null) {
            Text(
                text = errorBrgState.deskripsi,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = barangEvent.harga,
            onValueChange = {
                val filteredInput = it.filter { char -> char.isDigit() }
                onValueChange(barangEvent.copy(harga = filteredInput))
            },
            label = { Text("Harga") },
            leadingIcon = {
                Icon(
                    painter = painterResource(R.drawable.money),
                    contentDescription = "Harga Icon",
                    tint = Color(0xFF6200EA)
                )
            },
            placeholder = { Text("Masukkan Harga") },
            isError = errorBrgState.harga != null,
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6200EA),
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red
            )
        )
        if (errorBrgState.harga != null) {
            Text(
                text = errorBrgState.harga,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 2.dp)
            )
        }
    }
}

