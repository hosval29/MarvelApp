package com.example.marvel.presentation.screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.marvel.R
import com.example.marvel.core.utils.Utils.roundOffDecimal
import java.lang.Math.ceil
import java.lang.Math.floor
import kotlin.math.roundToInt

@Composable
fun RatingBar(
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 5,
    starsColor: Color = Color.Red,
    ratingSize: Dp = 12.dp
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
    ) {
        Text(
            text = roundOffDecimal(rating),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.width(4.dp))
        Row {
            repeat(filledStars) {
                Icon(
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null, tint = starsColor,
                    modifier = Modifier.size(ratingSize)
                )
            }
            if (halfStar) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_half),
                    contentDescription = null,
                    tint = starsColor,
                    modifier = Modifier.size(ratingSize)
                )
            }
            repeat(unfilledStars) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_star_border),
                    contentDescription = null,
                    tint = starsColor,
                    modifier = Modifier.size(ratingSize)
                )
            }
        }
    }
}