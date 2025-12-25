package com.example.homework3.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.homework3.ui.layout.Paddings
import com.example.homework3.ui.layout.Size

@Composable
fun MainList() {
    val cats = remember { mutableStateListOf<Int>() }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (cats.isEmpty()) {
            repeat(5) {
                cats.add(it)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(Paddings.small),
        verticalArrangement = Arrangement.spacedBy(Paddings.medium)
    ) {
        items(cats.size) { index ->
            if (index >= cats.size - 2 && !isLoading) {
                LaunchedEffect(Unit) {
                    isLoading = true
                    repeat(3) {
                        cats.add(cats.size + it)
                    }
                    isLoading = false
                }
            }

            MainItemCard(
                catId = index
            )
        }

        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(Size.loaderSize),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}