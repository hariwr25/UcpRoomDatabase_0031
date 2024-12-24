package com.example.ucp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.ucp2.ui.navigation.MainControllerPage
import com.example.ucp2.ui.theme.UCP2Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UCP2Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainControllerPage(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

