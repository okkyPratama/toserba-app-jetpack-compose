package com.dicoding.composesubmissionapp.ui.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.composesubmissionapp.R
import com.dicoding.composesubmissionapp.ViewModelFactory
import com.dicoding.composesubmissionapp.di.Injection
import com.dicoding.composesubmissionapp.ui.common.UiState
import com.dicoding.composesubmissionapp.ui.components.CartItem
import com.dicoding.composesubmissionapp.ui.components.OrderButton

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAddedProductOrder()
            }
            is UiState.Success -> {
                CartContent(
                    uiState.data,
                    onProductCountChanged = { productId, price ->
                        viewModel.updateProductOrder(productId, price)
                    },
                    onOrderButtonClicked = onOrderButtonClicked
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun CartContent(
    state: CartState,
    onProductCountChanged: (id: Long, price: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val shareMessage = stringResource(
        R.string.share_message,
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(R.string.menu_cart),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.productOrder, key = {it.product.id}) { item ->
                CartItem(
                    productId = item.product.id,
                    photo = item.product.photoUrl,
                    title = item.product.title,
                    totalPrice = item.product.price * item.count,
                    count = item.count,
                    onProductCountChanged = onProductCountChanged,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))

        OrderButton(
            text = stringResource(R.string.total_order, state.totalPrice),
            enabled = state.productOrder.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier
                .padding(16.dp)
        )
    }
}



