package com.dicoding.composesubmissionapp.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dicoding.composesubmissionapp.ui.theme.Shapes
import com.dicoding.composesubmissionapp.R
import com.dicoding.composesubmissionapp.ui.theme.ComposeSubmissionAppTheme

@Composable
fun CartItem(
    productId: Long,
    photo: String,
    title: String,
    totalPrice: Int,
    count: Int,
    onProductCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = photo,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.price,
                    totalPrice
                ),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        ProductCounter(
            orderId = productId,
            orderCount = count,
            onProductIncreased = { onProductCountChanged(productId, count + 1) },
            onProductDecreased = { onProductCountChanged(productId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )


    }

}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    ComposeSubmissionAppTheme{
        CartItem(
            1,
            "https://raw.githubusercontent.com/okkyPratama/composedummyimages/main/data-dummy-compose-app/product_1.jpg",
            "Jaket Hoodie Dicoding",
            65000,
            2,
            onProductCountChanged = { productId, count -> },
        )
    }
}