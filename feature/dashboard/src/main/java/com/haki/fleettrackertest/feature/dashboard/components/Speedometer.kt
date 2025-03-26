package com.haki.fleettrackertest.feature.dashboard.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Speedometer(
    modifier: Modifier = Modifier,
    currentSpeed: Int,
    maxSpeed: Int,
) {
    val sweepAngle = 180f
    val startAngle = 180f
    val speedRatio = currentSpeed.toFloat() / maxSpeed
    val width = 240.dp
    val primaryColor = MaterialTheme.colorScheme.primary
    val dangerColor = MaterialTheme.colorScheme.error

    val animatedSpeedRatio by animateFloatAsState(
        targetValue = currentSpeed.toFloat() / maxSpeed,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "Speedometer Animation"
    )

    val animatedSpeed by animateIntAsState(
        targetValue = currentSpeed,
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing),
        label = "Speed Text Animation"
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Current speed",
            fontSize = 34.sp,
            fontWeight = FontWeight.Bold
        )
        Canvas(modifier = Modifier.size(width, width/3)) {
            drawArc(
                color = Color.LightGray,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = false,
                size = Size(size.width, size.width),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
            drawArc(
                color = if (currentSpeed < 80) primaryColor else dangerColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle * animatedSpeedRatio,
                useCenter = false,
                size = Size(size.width, size.width),
                style = Stroke(width = 20.dp.toPx(), cap = StrokeCap.Round)
            )
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$currentSpeed",
                fontSize = 64.sp,
                fontWeight = FontWeight.Normal
            )
            Text(
                text = "km/h",
                fontSize = 20.sp,
                fontWeight = FontWeight.Normal
            )
        }
        Row{
            Text(
                text = "0",
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$maxSpeed",
                fontSize = 24.sp,
            )
        }
    }
}
