package com.example.homework2.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


@Composable
fun showToast(): (String) -> Unit {
    val context = LocalContext.current
    return remember(context) { { message: String ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    } }
}