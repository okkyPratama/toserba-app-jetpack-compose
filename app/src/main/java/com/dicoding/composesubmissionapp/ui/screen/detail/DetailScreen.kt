package com.dicoding.composesubmissionapp.ui.screen.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.dicoding.composesubmissionapp.R
import com.dicoding.composesubmissionapp.ViewModelFactory
import com.dicoding.composesubmissionapp.di.Injection
import com.dicoding.composesubmissionapp.ui.common.UiState
import com.dicoding.composesubmissionapp.ui.components.OrderButton
import com.dicoding.composesubmissionapp.ui.components.ProductCounter
import com.dicoding.composesubmissionapp.ui.theme.ComposeSubmissionAppTheme

@Composable
fun DetailScreen(
    productId: Long,
    viewModel: DetailProductViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    navigateBack: () -> Unit,
    navigateToCart: () -> Unit

) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getProductById(productId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailContent(
                    data.product.photoUrl,
                    data.product.title,
                    data.product.desc,
                    data.product.price,
                    data.count,
                    onBackClick = navigateBack,
                    onAddToCart = { count ->
                        viewModel.addToCart(data.product, count)
                        navigateToCart()
                    }
                )
            }

            is UiState.Error -> {}
        }

    }

}

@Composable
fun DetailContent(
    photo: String,
    title: String,
    desc: String,
    price: Int,
    count: Int,
    onBackClick: () -> Unit,
    onAddToCart: (count: Int) -> Unit,
    modifier: Modifier = Modifier,

    ) {

    var totalPoint by rememberSaveable { mutableStateOf(0) }
    var orderCount by rememberSaveable { mutableStateOf(count) }

    Column(modifier = modifier.fillMaxHeight()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())

        ) {
            Box {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    modifier = Modifier
                        .padding(16.dp)
                        .clickable { onBackClick() }
                )
            }
            AsyncImage(
                model = photo,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = stringResource(R.string.price, price),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = MaterialTheme.colors.secondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Justify,
                )

            }

            Column(
                modifier = Modifier.padding( 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProductCounter(
                    1,
                    orderCount,
                    onProductIncreased = { orderCount++ },
                    onProductDecreased = { if (orderCount > 0) orderCount-- },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)

                )


            }


        }

        Box(modifier = Modifier.fillMaxWidth()) {
            totalPoint = price * orderCount
            OrderButton(
                text = stringResource(R.string.add_to_cart, totalPoint),
                enabled = orderCount > 0,
                onClick = {
                    onAddToCart(orderCount)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)


            )
        }
    }


}


@Preview(showBackground = true)
@Composable
fun DetailContentPreview() {
    ComposeSubmissionAppTheme {
        DetailContent(
            "https://raw.githubusercontent.com/okkyPratama/composedummyimages/main/data-dummy-compose-app/product_1.jpg",
            "Sepatu Sneakers",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Proin sagittis lectus id risus lacinia sollicitudin. Nam scelerisque porttitor ullamcorper. Quisque laoreet egestas pretium. Vestibulum eleifend, ipsum ac luctus fermentum, lectus felis porttitor tortor, vitae condimentum sapien libero id tellus.",
            50000,
            1,
            onBackClick = {},
            onAddToCart = {}
        )
    }
}