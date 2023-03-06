package com.example.marvel.presentation.screen.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.marvel.R
import com.example.marvel.presentation.navigation.Route
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    val scale = remember { Animatable(0f) }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.3f,
            animationSpec = tween(
                durationMillis = 500,
                easing = {
                    OvershootInterpolator(2f).getInterpolation(it)
                }
            )
        )
        delay(3000L)
        navController.popBackStack()
        navController.navigate(Route.COMICS_HOME_SCREEN)
    }

    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xFFEC1C23))
    ) {

        val (textMarvel, textPowerBy, textCoding, textVersion) = createRefs()
        val bottomGuideline = createGuidelineFromBottom(16.dp)

        Text(
            text = "MARVEL",
            color = Color(0xFFFEFEFE),
            fontSize = 160.sp,
            fontFamily = FontFamily(
                Font(R.font.marvel_regular)
            ),
            modifier = Modifier
                .constrainAs(textMarvel) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .scale(scale.value),
        )
        Text(
            text = "Powered by",
            style = TextStyle(
                color = Color(0xFFFEFEFE)
            ),
            modifier = Modifier.constrainAs(textPowerBy) {
                start.linkTo(textMarvel.start)
                end.linkTo(textMarvel.end)
                top.linkTo(textMarvel.bottom, margin = 16.dp)
                bottom.linkTo(parent.bottom)
            }
        )
        Text(
            text = "<{}> Coding with Oswaldo",
            color = Color(0xAAF0E221),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.constrainAs(textCoding) {
                start.linkTo(textMarvel.start)
                end.linkTo(textMarvel.end)
                top.linkTo(textPowerBy.bottom, margin = 4.dp)
                //bottom.linkTo(parent.bottom)
            }
        )
        Text(
            text = "Version: 1.0",
            color = Color(0xFFFEFEFE),
            modifier = Modifier.constrainAs(textVersion) {
                start.linkTo(textMarvel.start)
                end.linkTo(textMarvel.end)
                bottom.linkTo(bottomGuideline)
            }
        )
    }
}
