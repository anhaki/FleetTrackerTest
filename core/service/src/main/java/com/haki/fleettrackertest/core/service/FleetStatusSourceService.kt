package com.haki.fleettrackertest.core.service

import android.app.Notification
import android.app.Notification.FLAG_ONGOING_EVENT
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.os.Vibrator
import android.util.Log
import androidx.core.app.NotificationCompat
import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.usecase.InsertStatusLogUseCase
import com.haki.fleettrackertest.core.utils.Constants
import com.haki.fleettrackertest.core.utils.StatusActions
import com.haki.fleettrackertest.core.utils.StatusActions.SUBSCRIBE
import com.haki.fleettrackertest.core.utils.StatusActions.UNSUBSCRIBE
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates
import kotlin.random.Random

@AndroidEntryPoint
class FleetStatusSourceService : Service() {
    @Inject
    lateinit var insertStatusLogUseCase: InsertStatusLogUseCase

    private val scope = CoroutineScope(Dispatchers.IO + Job())
    private var isRunning by Delegates.notNull<Boolean>()
    private var speed = 0
    private var engineOn = false
    private var doorClosed = false

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            SUBSCRIBE.toString() -> {
                isRunning = true
                startSimulation()
            }

            UNSUBSCRIBE.toString() -> {
                isRunning = false
                stopSelf()
            }

            else -> {
            }
        }
        return START_STICKY
    }

    private fun startSimulation() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        suspend fun updateStatusAndNotify() {
            speed = Random.nextInt(0, 100)
            engineOn = Random.nextBoolean()
            doorClosed = Random.nextBoolean()

            val statusLog = StatusLog(
                id = 0,
                speed = speed,
                engineOn = engineOn,
                doorClosed = doorClosed,
                date = System.currentTimeMillis()
            )
            insertStatusLogUseCase(statusLog)

            val notification = createNotification(speed, engineOn, doorClosed).apply {
                flags = FLAG_ONGOING_EVENT
            }
            notificationManager.notify(1, notification)
        }

        scope.launch {
            updateStatusAndNotify()
            if (Build.VERSION.SDK_INT < 34) {
                startForeground(1, createNotification(speed, engineOn, doorClosed))
            } else {
                startForeground(1, createNotification(speed, engineOn, doorClosed),
                    FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
            }

            while (isRunning) {
                delay(3000)
                if (!isRunning) break
                updateStatusAndNotify()
            }
        }
    }

    private fun createNotification(speed: Int, engineOn: Boolean, doorClosed: Boolean): Notification {
        val unsubPendingIntent = createActionIntent(this, UNSUBSCRIBE)
        val engineStat = if(engineOn) "On" else "Off"
        val doorStat = if(doorClosed) "Closed" else "Opened"

        return NotificationCompat.Builder(this, Constants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_warning)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_warning))
            setContentTitle("Connected")
            setContentText("Spd: $speed, Eng: $engineStat, Door: $doorStat")
            setOngoing(true)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            addAction(R.drawable.ic_warning, "Disconnect", unsubPendingIntent)
            setDeleteIntent(unsubPendingIntent)
            setStyle(NotificationCompat.DecoratedCustomViewStyle())
        }.build()
    }

    private fun createActionIntent(
        context: Context,
        action: StatusActions
    ): PendingIntent {
        val intent = Intent(context, FleetStatusSourceService::class.java).apply {
            this.action = action.toString()
        }

        return PendingIntent.getService(
            context,
            action.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
    }

}