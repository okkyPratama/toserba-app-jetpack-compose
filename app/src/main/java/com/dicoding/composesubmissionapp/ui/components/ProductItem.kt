package com.dicoding.composesubmissionapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.composesubmissionapp.ui.theme.ComposeSubmissionAppTheme

@Composable
fun ProductItem(
    title: String,
    photoUrl: String,
    modifier: Modifier = Modifier,

    ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {

        AsyncImage(
            model = photoUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
        )
        Text(
            text = title,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(start = 16.dp)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    ComposeSubmissionAppTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .clickable{ },
            elevation = 5.dp
        ) {
            ProductItem(
                title = "Sepatu Sneakers",
                photoUrl= "https://raw.githubusercontent.com/okkyPratama/composedummyimages/main/data-dummy-compose-app/product_1.jpg"
            )
        }

    }
}