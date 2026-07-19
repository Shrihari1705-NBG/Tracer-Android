package com.shrihari.smartcampusnavigator.utils

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val locationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    private val _locationState =
        MutableStateFlow(isLocationEnabled())

    val locationState: StateFlow<Boolean> =
        _locationState.asStateFlow()

    private val locationReceiver = object : BroadcastReceiver() {

        override fun onReceive(
            context: Context?,
            intent: Intent?
        ) {

            if (intent?.action == LocationManager.PROVIDERS_CHANGED_ACTION) {

                _locationState.value =
                    isLocationEnabled()

            }

        }

    }

    init {

        context.registerReceiver(
            locationReceiver,
            IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        )

    }

    fun isLocationEnabled(): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

            locationManager.isLocationEnabled

        } else {

            Settings.Secure.getInt(
                context.contentResolver,
                Settings.Secure.LOCATION_MODE,
                Settings.Secure.LOCATION_MODE_OFF
            ) != Settings.Secure.LOCATION_MODE_OFF

        }

    }

    fun openLocationSettings(activity: Activity) {

        activity.startActivity(
            Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        )

    }

}