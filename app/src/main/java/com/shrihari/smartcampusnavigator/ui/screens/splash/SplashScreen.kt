package com.shrihari.smartcampusnavigator.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.shrihari.smartcampusnavigator.R
import com.shrihari.smartcampusnavigator.ui.navigation.Screen
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavController
) {

    var startAnimation by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }
    var loadingText by remember { mutableStateOf("Initializing Tracer...") }

    val floatTransition = rememberInfiniteTransition(label = "float")

    val floatOffset by floatTransition.animateFloat(
        initialValue = -5f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2500,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        ),
        label = "float"
    )

    LaunchedEffect(Unit) {

        startAnimation = true

        delay(600)

        val messages = listOf(
            "Initializing Tracer...",
            "Loading Department Map...",
            "Preparing BLE Scanner...",
            "Loading Navigation Engine...",
            "Ready to Explore!"
        )

        for (i in 0..100) {

            progress = i / 100f

            loadingText = when {

                i < 20 -> messages[0]
                i < 40 -> messages[1]
                i < 60 -> messages[2]
                i < 85 -> messages[3]
                else -> messages[4]

            }

            delay(22)
        }

        loadingText = "Welcome to Tracer!"

        delay(500)

        navController.navigate(Screen.Home.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    val logoScale by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0.75f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "logoScale"
    )

    val logoOffset by animateFloatAsState(
        targetValue = if (startAnimation) 0f else -250f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = FastOutSlowInEasing
        ),
        label = "logoOffset"
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(700),
        label = "logoAlpha"
    )

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(250),
        label = "progress"
    )

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer {
                    translationY = logoOffset + floatOffset
                    scaleX = logoScale
                    scaleY = logoScale
                    alpha = logoAlpha
                },
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(R.drawable.tracer_logo),
                contentDescription = null,
                modifier = Modifier.width(180.dp)
            )

            Spacer(modifier = Modifier.height(28.dp))

            AnimatedVisibility(
                visible = startAnimation,
                enter = fadeIn(
                    animationSpec = tween(
                        600,
                        delayMillis = 400
                    )
                ) + slideInVertically(
                    initialOffsetY = { 40 }
                )
            ) {

                Text(
                    text = "Tracer",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(8.dp))

            AnimatedVisibility(
                visible = startAnimation,
                enter = fadeIn(
                    animationSpec = tween(
                        600,
                        delayMillis = 700
                    )
                )
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Indoor Navigation for Smart Campuses"
                    )

                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        text = loadingText,
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    LinearProgressIndicator(
                        progress = { animatedProgress },
                        modifier = Modifier
                            .width(320.dp)
                            .height(8.dp)
                            .clip(MaterialTheme.shapes.extraLarge)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "${(animatedProgress * 100).toInt()}%",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
