package com.dicoding.composesubmissionapp

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.composesubmissionapp.ui.navigation.NavigationItem
import com.dicoding.composesubmissionapp.ui.navigation.Screen
import com.dicoding.composesubmissionapp.ui.screen.about.AboutScreen
import com.dicoding.composesubmissionapp.ui.screen.cart.CartScreen
import com.dicoding.composesubmissionapp.ui.screen.detail.DetailScreen
import com.dicoding.composesubmissionapp.ui.screen.home.HomeScreen
import com.dicoding.composesubmissionapp.ui.theme.LightGray

@Composable
fun ComposeSubmissionApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),

    ) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            if (currentRoute != Screen.DetailProduct.route && currentRoute != Screen.Cart.route ) {
                TopAppBar(
                    title = {
                        Text(text = stringResource(id =R.string.app_title))
                    }
                )
            }

        },

        bottomBar = {
            if (currentRoute != Screen.DetailProduct.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = { productId ->
                        try {
                            Log.d("NAVIGATION", "Navigating to product with id: $productId")
                            navController.navigate(Screen.DetailProduct.createRoute(productId))
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }

                    }
                )
            }
            composable(Screen.Cart.route) {
                val context = LocalContext.current
                CartScreen(onOrderButtonClicked = { message ->
                    shareOrder(context, message)
                })
            }
            composable(Screen.About.route) {
                AboutScreen()
            }
            composable(
                route = Screen.DetailProduct.route,
                arguments = listOf(navArgument("productId") { type = NavType.LongType})
            ) {
                val id = it.arguments?.getLong("productId") ?: -1L
                DetailScreen(
                    productId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                    ,
                    navigateToCart = {
                        navController.popBackStack()
                        navController.navigate(Screen.Cart.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )


            }

        }
    }
}


private fun shareOrder(context: Context, summary: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.order))
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.order)
        )
    )
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.home_page),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.cart_page),
                icon = Icons.Default.ShoppingCart,
                screen = Screen.Cart
            ),
            NavigationItem(
                title = stringResource(R.string.about_page),
                icon = Icons.Default.AccountCircle,
                screen = Screen.About
            ),
        )
        navigationItems.map {
            BottomNavigationItem(
                icon = {
                    Icon(
                        imageVector = it.icon,
                        contentDescription = it.title
                    )
                },
                label = {
                    Text(it.title)
                },
                selected = currentRoute == it.screen.route,
                unselectedContentColor = LightGray,
                onClick = {
                    navController.navigate(it.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                }
            )
        }
    }

}

