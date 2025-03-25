package com.haki.fleettrackertest.feature.dashboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.haki.fleettrackertest.feature.dashboard.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardTopBar(
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = 36.dp, vertical = 40.dp),
        title = {
            Text(
                text = stringResource(R.string.dashboard),
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(MaterialTheme.colorScheme.primary)
    )
}