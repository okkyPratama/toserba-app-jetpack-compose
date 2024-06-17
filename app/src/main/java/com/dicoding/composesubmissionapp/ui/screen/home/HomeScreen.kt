package com.dicoding.composesubmissionapp.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.composesubmissionapp.ViewModelFactory
import com.dicoding.composesubmissionapp.di.Injection
import com.dicoding.composesubmissionapp.model.ProductOrder
import com.dicoding.composesubmissionapp.ui.common.UiState
import com.dicoding.composesubmissionapp.ui.components.ProductItem
import com.dicoding.composesubmissionapp.ui.components.SearchBar


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    ),
    navigateToDetail: (Long) -> Unit,

    ) {

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->

        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllProducts()
            }
            is UiState.Success -> {

                HomeContent(
                    productOrder = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                    viewModel = viewModel
                )
            }
            is UiState.Error -> {}

        }


    }

}

@Composable
fun HomeContent(
    productOrder: List<ProductOrder>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel

    ) {

    val query = viewModel.query.collectAsState()
    Box(modifier = modifier) {
        LazyColumn {
            item {
                SearchBar(
                    query = query.value,
                    onQueryChange = viewModel::searchProducts,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            items(productOrder) { data ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    elevation = 5.dp
                ) {
                    ProductItem(
                        title = data.product.title,
                        photoUrl = data.product.photoUrl,
                        modifier = Modifier
                            .clickable { navigateToDetail(data.product.id) }

                    )
                }


            }
        }

    }


}