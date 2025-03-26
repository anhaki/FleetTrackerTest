package com.haki.fleettrackertest.feature.log.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.haki.fleettrackertest.core.domain.model.StatusLog
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun LogItem(log: StatusLog) {
    val dateTime = Instant.fromEpochMilliseconds(log.date)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    val formattedDate = "${dateTime.dayOfMonth}-${dateTime.monthNumber}-${dateTime.year}"
    val formattedTime = "${dateTime.hour}:${dateTime.minute}:${dateTime.second}"

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFBBDEFB)
        ) ,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: $formattedDate", fontWeight = FontWeight.Bold)
            Text(text = "Time: $formattedTime")
            Text(text = "Speed: ${log.speed} km/h")
            Text(text = "Engine: ${if (log.engineOn) "Engine On" else "Engine Off"}")
            Text(text = "Door: ${if (log.doorClosed) "Closed" else "Open"}")
        }
    }
}
