package com.example.ucp2.ui.view.barang

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.barang.DetailBarangViewModel
import com.example.ucp2.data.entity.Barang
import com.example.ucp2.ui.viewmodel.barang.DetailBrgUiState
import com.example.ucp2.ui.viewmodel.barang.toBarangEntity
import com.example.ucp22.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailBrgView(
    viewModel: DetailBarangViewModel = viewModel(factory = PenyediaViewModel.Factory),
    onBackArrow: () -> Unit = { },
    onEditClick: (String) -> Unit = { },
    onDeleteClick: () -> Unit = { }
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Data Barang") }, // Menggunakan Text untuk judul
                navigationIcon = {
                    IconButton(onClick = onBackArrow) { // Tombol kembali
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = { // Ikon tambahan di kanan
                    Icon(
                        painter = painterResource(id = R.drawable.box),
                        contentDescription = "Box Icon",
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEditClick(viewModel.detailBrgUiState.value.detailUiBrgEvent.id)
                },
                shape = MaterialTheme.shapes.medium,
                containerColor = Color(0xFF6200EA), // Warna ungu
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        val detailBrgUiState by viewModel.detailBrgUiState.collectAsState()

        BarangDetailBody(
            modifier = Modifier.padding(innerPadding),
            detailBrgUiState = detailBrgUiState,
            onDeleteClick = {
                viewModel.deleteBrg()
                onDeleteClick()
            }
        )
    }
}

@Composable
fun BarangDetailBody(
    modifier: Modifier = Modifier,
    detailBrgUiState: DetailBrgUiState,
    onDeleteClick: () -> Unit = { }
) {
    var deleteConfirmationRequired by rememberSaveable { mutableStateOf(false) }

    when {
        detailBrgUiState.isLoading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        detailBrgUiState.isUiBarangEventNotEmpty -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF3F4F6),
                                Color(0xFFD7E8FF)
                            )
                        )
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Detail Barang
                ItemDetailBrg(
                    barang = detailBrgUiState.detailUiBrgEvent.toBarangEntity(),
                    modifier = modifier
                )

                // Tombol Delete
                Button(
                    onClick = { deleteConfirmationRequired = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Delete,
                        contentDescription = "Delete",
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Hapus Data", color = Color.White, fontWeight = FontWeight.Bold)
                }

                // Dialog Konfirmasi Delete
                if (deleteConfirmationRequired) {
                    DeleteConfirmationDialog(
                        onDeleteConfirm = {
                            deleteConfirmationRequired = false
                            onDeleteClick()
                        },
                        onDeleteCancel = { deleteConfirmationRequired = false },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }

        detailBrgUiState.isUiBarangEmpty -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Data tidak ditemukan",
                    color = MaterialTheme.colorScheme.error,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun ItemDetailBrg(
    modifier: Modifier = Modifier,
    barang: Barang
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp), // Memberikan padding di sekitar kartu
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // ID
            ComponentDetailBrg(title = "ID", content = barang.id.toString()) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "ID Icon",
                    tint = Color(0xFF6200EA),
                    modifier = Modifier.size(32.dp)
                )
            }

            // Nama Barang
            ComponentDetailBrg(title = "Nama Barang", content = barang.namaBarang) {
                Icon(
                    painter = painterResource(id = R.drawable.item),
                    contentDescription = "Nama Barang Icon",
                    tint = Color(0xFF6200EA),
                    modifier = Modifier.size(32.dp)
                )
            }

            // Deskripsi
            ComponentDetailBrg(title = "Deskripsi", content = barang.deskripsi) {
                Icon(
                    imageVector = Icons.Filled.Info,
                    contentDescription = "Deskripsi Icon",
                    tint = Color(0xFF6200EA),
                    modifier = Modifier.size(32.dp)
                )
            }

            // Harga
            ComponentDetailBrg(title = "Harga", content = "Rp${barang.harga}") {
                Icon(
                    painter = painterResource(id = R.drawable.money),
                    contentDescription = "Harga Icon",
                    tint = Color(0xFF6200EA),
                    modifier = Modifier.size(32.dp)
                )
            }

            // Stok
            ComponentDetailBrg(title = "Stok", content = barang.stok.toString()) {
                Icon(
                    painter = painterResource(id = R.drawable.stock),
                    contentDescription = "Stok Icon",
                    tint = Color(0xFF6200EA),
                    modifier = Modifier.size(32.dp)
                )
            }

            // Nama Supplier
            ComponentDetailBrg(title = "Nama Supplier", content = barang.namaSupplier) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Nama Supplier Icon",
                    tint = Color(0xFF6200EA),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}

@Composable
private fun DeleteConfirmationDialog(
    onDeleteConfirm: () -> Unit,
    onDeleteCancel: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = { },
        title = { Text("Konfirmasi Hapus") },
        text = { Text("Apakah Anda yakin ingin menghapus data ini?") },
        confirmButton = {
            TextButton(onClick = onDeleteConfirm) {
                Text("Ya", color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDeleteCancel) {
                Text("Batal")
            }
        }
    )
}

@Composable
fun ComponentDetailBrg(
    modifier: Modifier = Modifier,
    title: String,
    content: String,
    iconContent: @Composable () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        iconContent()
        Column {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                text = content,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            )
        }
    }
}
