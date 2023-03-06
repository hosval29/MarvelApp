package com.example.marvel.presentation.screen.comics.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.marvel.R
import com.example.marvel.presentation.components.ComicInfoRowList
import com.example.marvel.presentation.screen.components.RatingBar
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComicDetailScreen(
    comicId: String,
    viewModel: ComicDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val context = LocalContext.current

    LaunchedEffect(comicId) {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.getComicById(comicId)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.title_toolbar_comics_detail),
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
                navigationIcon = {
                    IconButton(onClick = {
                        onNavigateBack()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { padding ->

        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(padding)
        ) {

            val (
                comicName,
                comicPrice,
                comicRating,
                comicAvatar,
                comicDescription,
                comicInfoCreators,
                comicInfoCharacters,
                comicInfoStories
            ) = createRefs()

            val topGuideline = createGuidelineFromTop(16.dp)
            val startGuideline = createGuidelineFromStart(24.dp)
            val endGuideline = createGuidelineFromEnd(24.dp)

            createHorizontalChain(comicRating, comicPrice, chainStyle = ChainStyle.SpreadInside)

            Image(
                painter = rememberAsyncImagePainter(
                    model = ImageRequest.Builder(context)
                        .data(state.comicDetail.image)
                        .error(R.drawable.ic_catching_marvel)
                        .placeholder(R.drawable.ic_catching_marvel)
                        .build()
                ),
                contentScale = ContentScale.FillBounds,
                contentDescription = state.comicDetail.title,
                modifier = Modifier
                    .constrainAs(comicAvatar) {
                        top.linkTo(topGuideline)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        width = Dimension.value(100.dp)
                        height = Dimension.value(150.dp)
                    }
                    .clip(RoundedCornerShape(6.dp))
            )

            Text(
                text = state.comicDetail.title.replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.ROOT
                    ) else it.toString()
                },
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                modifier = Modifier
                    .constrainAs(comicName) {
                        top.linkTo(comicAvatar.bottom, margin = 12.dp)
                        start.linkTo(startGuideline)
                        end.linkTo(endGuideline)
                        width = Dimension.fillToConstraints
                    }
            )

            RatingBar(
                rating = state.comicDetail.rating,
                modifier = Modifier
                    .constrainAs(comicRating) {
                        top.linkTo(comicName.bottom)
                        start.linkTo(startGuideline)
                    }
                    .padding(horizontal = 24.dp, vertical = 8.dp),
                ratingSize = 16.dp
            )

            Text(
                text = "U.S. PRICE: $15.99",
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 8.sp,
                modifier = Modifier
                    .constrainAs(comicPrice) {
                        top.linkTo(comicRating.top)
                        bottom.linkTo(comicRating.bottom)
                    }
                    .padding(horizontal = 24.dp, vertical = 8.dp)
            )

            Text(
                text = state.comicDetail.description,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Left,
                modifier = Modifier.constrainAs(comicDescription) {
                    top.linkTo(comicRating.bottom, margin = 12.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(endGuideline)
                    width = Dimension.fillToConstraints
                }
            )

            ComicInfoRowList(
                items = state.comicDetail.creators,
                modifier = Modifier.constrainAs(comicInfoCreators) {
                    top.linkTo(comicDescription.bottom, margin = 20.dp)
                    start.linkTo(startGuideline)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = stringResource(id = R.string.title_creator)
            )

            ComicInfoRowList(
                items = state.comicDetail.characters,
                modifier = Modifier.constrainAs(comicInfoCharacters) {
                    top.linkTo(comicInfoCreators.bottom)
                    start.linkTo(startGuideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = stringResource(id = R.string.title_characters)
            )

            ComicInfoRowList(
                items = state.comicDetail.stories,
                modifier = Modifier.constrainAs(comicInfoStories) {
                    top.linkTo(comicInfoCharacters.bottom)
                    start.linkTo(startGuideline)
                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                },
                title = stringResource(id = R.string.title_stories)
            )
        }
    }
}