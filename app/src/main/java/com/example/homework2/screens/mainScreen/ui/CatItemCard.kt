package com.example.homework2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.homework2.R
import com.example.homework2.ui.layout.Paddings
import com.example.homework2.ui.layout.Size
import com.example.homework2.viewModel.CatViewModel

@Composable
fun CatItemCard(catId: Int) {
    val viewModel = CatViewModel()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Paddings.small),
        shape = RoundedCornerShape(Size.baseCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = Paddings.extraSmall),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(Paddings.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CatImageView(catId = catId, viewModel = viewModel)

            HorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Paddings.small),
                thickness = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = LocalContext.current.getString(R.string.randomCat) + " $catId",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorite",
                    modifier = Modifier.size(Size.heartSize),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}