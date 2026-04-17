package com.example.life_cycleorganizer.ui.utils

import android.content.Context
import android.media.RingtoneManager
import android.net.Uri

object AlarmHelper {

    fun playAlarm(context: Context) {

        val alarmUri: Uri? =
            RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val ringtone = RingtoneManager.getRingtone(context, alarmUri)

        ringtone?.play()
    }
}