package com.zaid.elkdocsassignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.zaid.elkdocsassignment.ui.theme.ElkDocsAssignmentTheme
import kotlinx.coroutines.delay
import kotlin.math.cos
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElkDocsAssignmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black)
                            .padding(innerPadding), contentAlignment = Alignment.Center
                    ) {
                        WavyClock()
                    }
                }
            }
        }
    }
}

@Composable
fun WavyClock() {
    val infiniteTransition = rememberInfiniteTransition()

    // Animating the progress of the wavy effect
    val progress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val progress1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing)
        )
    )
    val progress3 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 700, easing = LinearEasing)
        )
    )

    val infiniteTransitionForBox = rememberInfiniteTransition()
    val sizeOfBox1 by infiniteTransitionForBox.animateFloat(
        initialValue = 150f,
        targetValue = 250f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = EaseInOut),
            repeatMode = RepeatMode.Reverse
        )
    )
    val sizeOfBox2 by infiniteTransitionForBox.animateFloat(
        initialValue = 150f,
        targetValue = 260f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val sizeOfBox3 by infiniteTransitionForBox.animateFloat(
        initialValue = 150f,
        targetValue = 220f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = EaseInBounce),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(modifier = Modifier.size(300.dp)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(45.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Canvas(modifier = Modifier.size(sizeOfBox1.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2
                val waveFrequency = 5
                val waveAmplitude = 12.dp.toPx()

                drawWavyCircle(center, radius, waveFrequency, waveAmplitude, progress)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            contentAlignment = Alignment.TopStart
        ) {
            Canvas(modifier = Modifier.size(sizeOfBox2.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2
                val waveFrequency = 4
                val waveAmplitude = 10.dp.toPx()

                drawWavyCircle(center, radius, waveFrequency, waveAmplitude, progress1)
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(40.dp), contentAlignment = Alignment.Center) {
            Canvas(modifier = Modifier.size(sizeOfBox3.dp)) {
                val center = Offset(size.width / 2, size.height / 2)
                val radius = size.minDimension / 2
                val waveFrequency = 4
                val waveAmplitude = 13.dp.toPx()

                drawWavyCircle(center, radius, waveFrequency, waveAmplitude, progress3)
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .size(250.dp)
                .background(color = Color.Black, shape = CircleShape)
                .border(
                    width = 2.dp,
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color.Magenta,
                            Color.Red,
                            Color.Blue
                        )
                    ), shape = CircleShape
                ), contentAlignment = Alignment.Center
        ) {
            ClockHands()
        }
    }
}

fun DrawScope.drawWavyCircle(
    center: Offset,
    radius: Float,
    waveFrequency: Int,
    waveAmplitude: Float,
    progress: Float
) {
    val path = Path().apply {
        for (angle in 0 until 360) {
            val radian = Math.toRadians(angle.toDouble())
            val wave =
                sin(waveFrequency * radian + (progress * 2 * Math.PI)).toFloat() * waveAmplitude
            val x = center.x + (radius + wave) * cos(radian).toFloat()
            val y = center.y + (radius + wave) * sin(radian).toFloat()
            if (angle == 0) {
                moveTo(x, y)
            } else {
                lineTo(x, y)
            }
        }
        close()
    }

    drawPath(
        path = path,
        brush = Brush.horizontalGradient(
            colors = listOf(Color.Magenta, Color.Red, Color.Blue)
        ),
        style = Stroke(width = 30.dp.toPx())
    )
}

@Composable
fun ClockHands() {
    val secondHandLength = 100.dp
    val minuteHandLength = 70.dp
    val hourHandLength = 50.dp

    val secondHandColor = Color.Cyan
    val minuteHandColor = Color.Green
    val hourHandColor = Color.Red

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
        }
    }
    val time = remember { mutableStateOf(java.util.Calendar.getInstance()) }
    LaunchedEffect(Unit) {
        while (true) {
            time.value = java.util.Calendar.getInstance()
            delay(1000)
        }
    }
    Canvas(modifier = Modifier.size(200.dp)) {
        val center = Offset(size.width / 2, size.height / 2)


        val secondAngle = time.value.get(java.util.Calendar.SECOND) * 6f
        val minuteAngle = time.value.get(java.util.Calendar.MINUTE) * 6f + secondAngle / 60f
        val hourAngle = (time.value.get(java.util.Calendar.HOUR) % 12) * 30f + minuteAngle / 12f

        drawHand(center, secondHandLength, secondAngle, secondHandColor)
        drawHand(center, minuteHandLength, minuteAngle, minuteHandColor)
        drawHand(center, hourHandLength, hourAngle, hourHandColor)
    }
}

fun DrawScope.drawHand(center: Offset, length: Dp, angle: Float, color: Color) {
    val radian = Math.toRadians(angle.toDouble() - 90)
    val end = Offset(
        x = center.x + length.toPx() * cos(radian).toFloat(),
        y = center.y + length.toPx() * sin(radian).toFloat()
    )

    drawLine(
        color = color,
        start = center,
        end = end,
        strokeWidth = 4.dp.toPx()
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewAnimatedClock() {
    WavyClock()
}