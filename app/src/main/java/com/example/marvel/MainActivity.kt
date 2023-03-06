package com.example.marvel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.marvel.presentation.navigation.Route
import com.example.marvel.presentation.screen.comics.details.ComicDetailScreen
import com.example.marvel.presentation.screen.comics.favorites.FavoritesScreen
import com.example.marvel.presentation.screen.comics.home.ComicsHomeScreen
import com.example.marvel.presentation.screen.splash.SplashScreen
import com.example.marvel.ui.theme.MarvelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MarvelTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {

                    val navController = rememberNavController()

                    Scaffold { paddingValues ->
                        NavHost(
                            navController,
                            startDestination = Route.SPLASH_SCREEN,
                            modifier = Modifier.padding(paddingValues)
                        ) {
                            composable(Route.SPLASH_SCREEN) {
                                SplashScreen(navController = navController)
                            }
                            composable(Route.COMICS_HOME_SCREEN) {
                                ComicsHomeScreen(
                                    onNavigateComicDetail = { comicId ->
                                        navController.navigate(
                                            Route.COMICS_DETAILS_SCREEN + "/$comicId"
                                        )
                                    },
                                    onNavigateComicsFavorites = {
                                        navController.navigate(
                                            route = Route.COMICS_FAVORITE_SCREEN
                                        )
                                    }
                                )
                            }
                            composable(
                                route = Route.COMICS_DETAILS_SCREEN + "/{comicId}",
                                arguments = listOf(
                                    navArgument("comicId") {
                                        type = NavType.StringType
                                    }
                                )
                            ) {
                                val comicId = it.arguments?.getString("comicId")!!
                                ComicDetailScreen(
                                    comicId = comicId,
                                    onNavigateBack = {
                                        navController.navigateUp()
                                    }
                                )
                            }
                            composable(Route.COMICS_FAVORITE_SCREEN) {
                                FavoritesScreen(
                                    onNavigateBack = {
                                        navController.navigateUp()
                                    },
                                    onNavigateComicDetail = { comicId ->
                                        navController.navigate(
                                            Route.COMICS_DETAILS_SCREEN + "/$comicId"
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
