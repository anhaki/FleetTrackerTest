package com.haki.fleettrackertest.feature.dashboard

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haki.fleettrackertest.core.utils.Status
import com.haki.fleettrackertest.feature.dashboard.components.Speedometer
import com.haki.fleettrackertest.feature.dashboard.components.StatusItem

@Composable
fun DashboardScreen(
    viewModel: DashboardViewmodel = hiltViewModel()
) {
    val engineState by viewModel.engineState.collectAsState()
    val doorState by viewModel.doorState.collectAsState()

    Column {
        Box{
            Spacer(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .height(150.dp)
                    .fillMaxWidth()
            )
            Speedometer(
                modifier = Modifier
                    .padding(16.dp)
                    .shadow(elevation = 3.dp, shape = RoundedCornerShape(14.dp))
                    .clip(RoundedCornerShape(14.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .padding(34.dp)
                    .fillMaxWidth(),
                currentSpeed = 38,
                maxSpeed = 120,
            )
        }
        StatusSection(engineState, doorState)
    }
}

@Composable
fun StatusSection(
    engineState: Status,
    doorState: Status,
) {
    Column(
        Modifier.padding(16.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Status",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(6.dp))
        Column {
            engineState.let {
                StatusItem(
                    icon = ImageVector.vectorResource(it.icon),
                    text = it.name,
                    status = it.status,
                    positiveStatusText = it.positiveStatusText,
                    negativeStatusText = it.negativeStatusText
                )
            }
            Spacer(Modifier.height(12.dp))
            doorState.let {
                StatusItem(
                    icon = ImageVector.vectorResource(it.icon),
                    text = it.name,
                    status = it.status,
                    positiveStatusText = it.positiveStatusText,
                    negativeStatusText = it.negativeStatusText
                )
            }
        }
    }
}
