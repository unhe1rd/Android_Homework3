package com.example.homework3.activity

import android.content.Intent
import android.provider.Settings
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import com.example.homework3.app.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val intent = Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION)
//        startActivity(intent)
        this.startActivityForResult(
            Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION),
            requestCode = 1010
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}