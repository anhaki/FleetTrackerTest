package com.haki.fleettrackertest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.haki.fleettrackertest.core.navigation.Screen
import com.haki.fleettrackertest.feature.common.BottomBar
import com.haki.fleettrackertest.feature.common.TopBar
import com.haki.fleettrackertest.feature.dashboard.DashboardScreen
import com.haki.fleettrackertest.feature.login.LoginScreen
import com.haki.fleettrackertest.feature.maps.MapsScreen
import com.haki.fleettrackertest.ui.theme.FleetTrackerTestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FleetTrackerTestTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Screen.Dashboard.route,
                ) {
                    composable(
                        route = Screen.Dashboard.route,
                    ) {
                        DashboardScreen(navController)
                    }
                    composable(
                        route = Screen.Maps.route,
                    ) {
                        MapsScreen(navController)
                    }
                    composable(
                        route = Screen.Login.route,
                    ) {
                        LoginScreen(navController)
                    }
                }
            }
        }
    }
}