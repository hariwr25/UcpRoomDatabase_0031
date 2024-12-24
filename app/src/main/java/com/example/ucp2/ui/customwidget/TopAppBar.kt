package com.example.ucp2.ui.customwidget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    actionIcon: Int, // Resource ID untuk ikon
    onActionClick: () -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    // Membungkus dengan Box untuk menerapkan gradasi
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF6200EA), // Ungu
                        Color(0xFF03DAC5)  // Hijau mint
                    )
                )
            )
    ) {
        CenterAlignedTopAppBar(
            modifier = modifier.fillMaxWidth(),
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = Color.Transparent, // Membuat latar belakang transparan
                titleContentColor = Color.White
            ),
            title = {
                Text(
                    text = title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.White
                )
            },
            navigationIcon = {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Navigate Back",
                        tint = Color.White
                    )
                }
            },
            actions = {
                IconButton(onClick = onActionClick) {
                    Icon(
                        painter = painterResource(id = actionIcon),
                        contentDescription = "Action Icon",
                        tint = Color.White
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBar() {
    TopAppBar(
        title = "Manage Barang",
        onBackClick = { },
        actionIcon = android.R.drawable.ic_menu_manage, // Menggunakan ikon bawaan Android
        onActionClick = { }
    )
}
