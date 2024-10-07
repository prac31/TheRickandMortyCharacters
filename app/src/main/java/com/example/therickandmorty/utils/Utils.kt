package com.example.therickandmorty.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

class Utils {
    companion object {
        fun vibrate(context: Context, duration: Long) {
            val vibrator = try {
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            } catch (e: Exception) {
                null
            }

            vibrator?.let {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    it.vibrate(
                        VibrationEffect.createOneShot(
                            duration,
                            VibrationEffect.DEFAULT_AMPLITUDE
                        )
                    )
                } else {
                    it.vibrate(duration)
                }
            }
        }
    }
}