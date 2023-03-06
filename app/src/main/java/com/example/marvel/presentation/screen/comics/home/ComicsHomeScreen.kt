package com.example.marvel.presentation.screen.comics.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.marvel.R
import com.example.marvel.presentation.components.ShimmerComicItem
import com.example.marvel.presentation.screen.comics.home.components.ComicItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicsHomeScreen(
    viewModel: ComicsHomeViewModel = hiltViewModel(),
    onNavigateComicDetail: (String) -> Unit,
    onNavigateComicsFavorites: () -> Unit
) {

    val lazyComicsItems = viewModel.dataComics.collectAsLazyPagingItems()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_app),
                        color = Color(0xFFFEFEFE),
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFEC1C23),
                    navigationIconContentColor = Color(0xFFFEFEFE),
                    actionIconContentColor = Color(0xFFFEFEFE)
                ),
                actions = {
                    IconButton(
                        onClick = { onNavigateComicsFavorites() }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null
                        )
                    }
                }
            )

        }
    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 12.dp,
                    end = 12.dp,
                    top = paddingValues.calculateTopPadding()
                )
        ) {
            items(lazyComicsItems.itemCount) { gridItem ->
                lazyComicsItems[gridItem]?.let { comic ->
                    ComicItem(
                        comic = comic,
                        onClickNavigateToComicDetail = { comicId ->
                            viewModel.onEvent(
                                ComicsHomeEvent.SetComicFavorite(comic)
                            )
                            onNavigateComicDetail(comicId)
                        }
                    )
                }
            }

            val loadState = lazyComicsItems.loadState.mediator
            if (loadState?.refresh == LoadState.Loading) {
                items(30) {
                    ShimmerComicItem()
                }
            }
            if (loadState?.append == LoadState.Loading) {
                items(2) {
                    ShimmerComicItem()
                }
            }
            if (loadState?.refresh is LoadState.Error || loadState?.append is LoadState.Error) {
                item(span = {GridItemSpan(maxLineSpan)}) {
                    val isPaginatingError =
                        (loadState.append is LoadState.Error) || lazyComicsItems.itemCount > 1
                    val error =
                        if (loadState.append is LoadState.Error) (loadState.append as LoadState.Error).error
                        else (loadState.refresh as LoadState.Error).error
                    val modifier = if (isPaginatingError) {
                        Modifier.padding(8.dp)
                    } else {
                        Modifier.fillMaxSize()
                    }
                    Column(
                        modifier = modifier,
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        if (!isPaginatingError) {
                            Icon(
                                modifier = Modifier
                                    .size(64.dp),
                                imageVector = Icons.Rounded.Warning, contentDescription = null
                            )
                        }
                        Text(
                            modifier = Modifier
                                .padding(8.dp),
                            text = stringResource(id = R.string.not_conexion_internet),
                            textAlign = TextAlign.Center,
                        )
                        Button(
                            onClick = {
                                lazyComicsItems.refresh()
                            },
                            content = {
                                Text(text = stringResource(id = R.string.refresh))
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White,
                            )
                        )
                    }
                }
            }
        }
    }

}