package com.haki.fleettrackertest.feature.dashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.haki.fleettrackertest.core.navigation.Screen
import com.haki.fleettrackertest.core.service.FleetStatusSourceService
import com.haki.fleettrackertest.core.utils.ServiceFlag
import com.haki.fleettrackertest.core.utils.Status
import com.haki.fleettrackertest.core.utils.StatusActions
import com.haki.fleettrackertest.feature.common.BottomBar
import com.haki.fleettrackertest.feature.common.TopBar
import com.haki.fleettrackertest.feature.dashboard.components.DisabledPermissionBanner
import com.haki.fleettrackertest.feature.dashboard.components.Speedometer
import com.haki.fleettrackertest.feature.dashboard.components.StatusItem
import kotlinx.coroutines.launch

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
    val isServiceRunning = remember { mutableStateOf(ServiceFlag.IS_SERVICE_RUNNING) }
    var hasNotificationPermission by remember {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else true
        )
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val warningMessage by viewModel.warningMessage.collectAsState()

    LaunchedEffect(speedState, engineState, doorState) {
        when {
            speedState > 80 -> viewModel.showWarning("⚠️ High Speed Detected!")
            speedState > 0 && !doorState.status -> viewModel.showWarning("⚠️ Door Open While Moving!")
            engineState.status -> viewModel.showWarning("🔧 Engine Started")
            !engineState.status -> viewModel.showWarning("🛑 Engine Stopped")
            else -> viewModel.dismissWarning()
        }
    }

    LaunchedEffect(warningMessage) {
        if (warningMessage.isNotEmpty()) {
            coroutineScope.launch {
                snackbarHostState.showSnackbar(warningMessage, duration = SnackbarDuration.Short)
            }
        }
    }
    DisposableEffect(Unit) {
        val observer = androidx.lifecycle.LifecycleEventObserver { _, event ->
            if (event == androidx.lifecycle.Lifecycle.Event.ON_RESUME) {
                hasNotificationPermission =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) == PackageManager.PERMISSION_GRANTED
                    } else true
            }
        }
        val lifecycle = (context as androidx.lifecycle.LifecycleOwner).lifecycle
        lifecycle.addObserver(observer)

        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
    val permissionRequest =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { result ->
            hasNotificationPermission = result
        }

    LaunchedEffect(isUserLoggedIn) {
        if (!isUserLoggedIn) {
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Dashboard.route) { inclusive = true }
            }
        }
    }

    if(isUserLoggedIn && !isCheckingSession){
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            modifier = Modifier,
            bottomBar = {
                BottomBar(navController = navController)
            },
            topBar = {
                TopBar(
                    title = stringResource(com.haki.fleettrackertest.feature.common.R.string.dashboard),
                    action = {
                        navController.navigate(Screen.Log.route)
                    }
                )
            },
            floatingActionButton = {
                Button(
                    onClick = {
                        val serviceIntent = if(!isServiceRunning.value) {
                            isServiceRunning.value = true
                            Intent(context, FleetStatusSourceService::class.java).also {
                                it.action = StatusActions.SUBSCRIBE.toString()
                            }
                        } else {
                            isServiceRunning.value = false
                            viewModel.updateEngineStatus(false)
                            viewModel.updateDoorStatus(false)
                            viewModel.updateSpeed(0)
                            Intent(context, FleetStatusSourceService::class.java).also {
                                it.action = StatusActions.UNSUBSCRIBE.toString()
                            }
                        }
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            context.startForegroundService(serviceIntent)
                        } else{
                            context.startService(serviceIntent)
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(!isServiceRunning.value) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    if(!isServiceRunning.value) Text("Start sensor") else Text("Stop sensor")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding)
            ){
                AnimatedVisibility(
                    visible = !hasNotificationPermission,
                    exit = shrinkVertically(
                        animationSpec = tween(durationMillis = 800)
                    ),
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        LaunchedEffect(Unit) {
                            permissionRequest.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                    }
                    DisabledPermissionBanner(
                        icon = ImageVector.vectorResource(R.drawable.ic_bell_slash),
                        title = "Notification is disabled",
                        description = "Enable for alerts function correctly",
                        actionText = "Settings",
                    ) {
                        val intent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                            }
                        } else {
                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                data = Uri.parse("package:${context.packageName}")
                            }
                        }
                        context.startActivity(intent)
                    }
                }
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
