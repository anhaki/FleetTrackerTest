package com.haki.fleettrackertest.feature.dashboard

import android.content.Intent
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haki.fleettrackertest.core.navigation.Screen
import com.haki.fleettrackertest.core.service.FleetStatusSourceService
import com.haki.fleettrackertest.core.utils.Status
import com.haki.fleettrackertest.core.utils.StatusActions
import com.haki.fleettrackertest.feature.common.BottomBar
import com.haki.fleettrackertest.feature.common.TopBar
import com.haki.fleettrackertest.feature.dashboard.components.Speedometer
import com.haki.fleettrackertest.feature.dashboard.components.StatusItem

@Composable
fun DashboardScreen(
    navController: NavController,
    viewModel: DashboardViewmodel = hiltViewModel()
) {
    val context = LocalContext.current
    val speedState by viewModel.speedState.collectAsState()
    val engineState by viewModel.engineState.collectAsState()
    val doorState by viewModel.doorState.collectAsState()
    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()
    val isCheckingSession by viewModel.isCheckingSession.collectAsState()

    LaunchedEffect(isUserLoggedIn) {
        if (!isUserLoggedIn) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Dashboard.route) { inclusive = true }
            }
        }
    }
    if(isUserLoggedIn && !isCheckingSession){
        Scaffold(
            modifier = Modifier,
            bottomBar = {
                BottomBar(navController = navController)
            },
            topBar = {
                TopBar(
                    title = stringResource(com.haki.fleettrackertest.feature.common.R.string.dashboard)
                )
            },
            floatingActionButton = {
                Button(
                    onClick = {
                        val subscribeIntent = Intent(context, FleetStatusSourceService::class.java).also {
                            it.action = StatusActions.SUBSCRIBE.toString()
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(subscribeIntent)
                        } else{
                            context.startService(subscribeIntent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Start sensor")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ){
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
                        currentSpeed = speedState,
                        maxSpeed = 100,
                    )
                }
                StatusSection(engineState, doorState)
            }
        }
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
