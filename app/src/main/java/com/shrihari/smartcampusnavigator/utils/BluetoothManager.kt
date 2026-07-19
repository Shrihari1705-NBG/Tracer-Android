package com.shrihari.smartcampusnavigator.utils

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BluetoothManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val manager =
            context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

        manager.adapter
    }

    private val _bluetoothState =
        MutableStateFlow(bluetoothAdapter?.isEnabled == true)

    val bluetoothState: StateFlow<Boolean> =
        _bluetoothState.asStateFlow()

    private val bluetoothStateReceiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context?, intent: Intent?) {

            if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED) {

                _bluetoothState.value =
                    bluetoothAdapter?.isEnabled == true

            }

        }

    }

    init {

        context.registerReceiver(
            bluetoothStateReceiver,
            IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)
        )

    }

    fun isBluetoothSupported(): Boolean {

        return bluetoothAdapter != null

    }

    fun isBluetoothEnabled(): Boolean {

        return bluetoothAdapter?.isEnabled == true

    }

    fun requestEnableBluetooth(activity: Activity) {

        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)

        activity.startActivity(intent)

    }

    fun openBluetoothSettings(activity: Activity) {

        val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)

        activity.startActivity(intent)

    }

}