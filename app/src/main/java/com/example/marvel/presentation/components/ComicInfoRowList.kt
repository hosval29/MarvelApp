package com.example.marvel.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvel.R
import com.example.marvel.domain.model.CharacterComic
import com.example.marvel.domain.model.CreatorComic
import com.example.marvel.domain.model.StoryComic

@Composable
fun ComicInfoRowList(
    modifier: Modifier = Modifier,
    items: List<Any> = emptyList(),
    title: String
) {

    Column(
        modifier = modifier
    ) {
        Text(
            text = title,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Start
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 5.dp),
            state = rememberLazyListState()
        ) {
            items(items) {
                when (it) {
                    is StoryComic -> {
                        ComicInfoRowItem(
                            title = it.name,
                            imageUrl = it.imageUrl ?: stringResource(id = R.string.not_image_url)
                        )
                    }
                    is CharacterComic -> {
                        ComicInfoRowItem(
                            title = it.name,
                            imageUrl = it.imageUrl
                        )
                    }
                    is CreatorComic -> {
                        ComicInfoRowItem(
                            title = it.name,
                            imageUrl = it.imageUrl
                        )
                    }
                }
            }
        }
    }
}