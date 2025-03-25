package com.haki.fleettrackertest.feature.maps

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay

@Composable
fun MapsScreen(
    innerPadding: PaddingValues,
    viewModel: MapsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val vehicleState by viewModel.vehicleState.collectAsState()
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState(position = vehicleState.currentPosition ?: LatLng(0.0, 0.0))

    LaunchedEffect(vehicleState.currentPosition) {
        vehicleState.currentPosition?.let { newPosition ->
            markerState.position = newPosition

            cameraPositionState.animate(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(newPosition)
                        .zoom(17f)
                        .build()
                ),
                durationMs = 1000
            )
        }
    }

    LaunchedEffect(Unit) {
        vehicleState.fullPath.firstOrNull()?.let { firstPosition ->
            cameraPositionState.move(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition.Builder()
                        .target(firstPosition)
                        .zoom(15f)
                        .build()
                )
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        if (vehicleState.fullPath.size > 1) {
            Polyline(
                points = vehicleState.fullPath,
                color = Color.Gray,
                width = 8f
            )
        }

        if (vehicleState.traveledPath.size > 1) {
            Polyline(
                points = vehicleState.traveledPath,
                color = Color.Blue,
                width = 8f
            )
        }

        vehicleState.currentPosition?.let {
            Marker(
                state = markerState,
                title = "Kendaraan Saya",
                icon = bitmapDescriptor(
                    context, R.drawable.ic_car
                )
            )
        }
    }
}

fun bitmapDescriptor(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {

    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )

    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}
