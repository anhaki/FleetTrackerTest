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
import androidx.core.app.NotificationCompat
import com.haki.fleettrackertest.core.domain.model.StatusLog
import com.haki.fleettrackertest.core.domain.usecase.InsertStatusLogUseCase
import com.haki.fleettrackertest.core.utils.Constants
import com.haki.fleettrackertest.core.utils.ServiceFlag
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
    private var lastAlertStatus = mutableMapOf(
        "doorOpen" to false,
        "highSpeed" to false,
        "engineOn" to false
    )

    override fun onCreate() {
        super.onCreate()
        ServiceFlag.IS_SERVICE_RUNNING = true
    }

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
            if (!engineOn || Random.nextInt(0, 4) == 0) {
                engineOn = Random.nextBoolean()
            }

            if (Random.nextInt(0, 3) == 0) {
                doorClosed = Random.nextBoolean()
            }

            speed = if (engineOn) {
                Random.nextInt(0, 100)
            } else {
                0
            }

            val statusLog = StatusLog(
                id = 0,
                speed = speed,
                engineOn = engineOn,
                doorClosed = doorClosed,
                date = System.currentTimeMillis()
            )
            insertStatusLogUseCase(statusLog)

            val notification = createServiceStatusNotification(speed, engineOn, doorClosed).apply {
                flags = FLAG_ONGOING_EVENT
            }
            notificationManager.notify(1, notification)

            checkAndManageWarnings(notificationManager)
        }

        scope.launch {
            updateStatusAndNotify()
            if (Build.VERSION.SDK_INT < 34) {
                startForeground(1, createServiceStatusNotification(speed, engineOn, doorClosed))
            } else {
                startForeground(1, createServiceStatusNotification(speed, engineOn, doorClosed),
                    FOREGROUND_SERVICE_TYPE_MEDIA_PLAYBACK)
            }

            while (isRunning) {
                delay(5000)
                if (!isRunning) break
                updateStatusAndNotify()
            }
        }
    }


    private fun createServiceStatusNotification(speed: Int, engineOn: Boolean, doorClosed: Boolean): Notification {
        val unsubPendingIntent = createActionIntent(this, UNSUBSCRIBE)
        val engineStat = if(engineOn) "On" else "Off"
        val doorStat = if(doorClosed) "Closed" else "Opened"

        return NotificationCompat.Builder(this, Constants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_connected)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_connected))
            setContentTitle("Connected")
            setContentText("Spd: $speed, Eng: $engineStat, Door: $doorStat")
            setOngoing(true)
            setPriority(NotificationCompat.PRIORITY_DEFAULT)
            addAction(R.drawable.ic_connected, "Disconnect", unsubPendingIntent)
            setDeleteIntent(unsubPendingIntent)
            setStyle(NotificationCompat.DecoratedCustomViewStyle())
        }.build()
    }

    private fun createAlertNotification(message: String): Notification {
        return NotificationCompat.Builder(this, Constants.ALERT_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.ic_warning)
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_warning))
            setContentTitle("⚠️ WARNING!")
            setContentText(message)
            setOngoing(true)
            setPriority(NotificationCompat.PRIORITY_MAX)
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

    private fun checkAndManageWarnings(notificationManager: NotificationManager) {
        val warningMessages = mutableMapOf<Int, String>()

        val isDoorOpenWarning = speed > 0 && !doorClosed
        val isHighSpeedWarning = speed > 80
        val isEngineOnWarning = engineOn

        if (lastAlertStatus["doorOpen"] != isDoorOpenWarning) {
            lastAlertStatus["doorOpen"] = isDoorOpenWarning
            if (isDoorOpenWarning) {
                warningMessages[1001] = "⚠️ Warning: Door Open While Moving!"
            } else {
                notificationManager.cancel(1001)
            }
        }

        if (lastAlertStatus["highSpeed"] != isHighSpeedWarning) {
            lastAlertStatus["highSpeed"] = isHighSpeedWarning
            if (isHighSpeedWarning) {
                warningMessages[1002] = "⚠️ Caution: High Speed!"
            } else {
                notificationManager.cancel(1002)
            }
        }

        if (lastAlertStatus["engineOn"] != isEngineOnWarning) {
            lastAlertStatus["engineOn"] = isEngineOnWarning

            val message = if (engineOn) "🔧 Engine Started" else "🛑 Engine Stopped"
            val alertNotification = createAlertNotification(message)
            notificationManager.notify(1003, alertNotification)

            scope.launch {
                delay(5000)
                notificationManager.cancel(1003)
            }
        }

        warningMessages.forEach { (id, message) ->
            val alertNotification = createAlertNotification(message)
            notificationManager.notify(id, alertNotification)
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        ServiceFlag.IS_SERVICE_RUNNING = false
    }
}