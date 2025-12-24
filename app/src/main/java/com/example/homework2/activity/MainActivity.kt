package com.example.homework2.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.homework2.ui.MainList
import com.example.homework2.ui.layout.Paddings

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = Paddings.extraExtraLarge),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoilRandomImageApp()
                }
            }
        }
    }
}

@Composable
fun CoilRandomImageApp() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Paddings.medium),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainList()
    }
}