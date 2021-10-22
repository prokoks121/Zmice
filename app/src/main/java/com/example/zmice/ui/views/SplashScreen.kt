package com.example.zmice.ui.views

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import com.example.zmice.R
import kotlinx.coroutines.delay

@Composable
fun SolashScreen(navigate:(String)-> Unit){
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }))
        delay(3000L)
        navigate("Home")
    }

    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
        .fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.snake),
                contentDescription = "Snake",
                modifier = Modifier.scale(scale.value))
    }
}