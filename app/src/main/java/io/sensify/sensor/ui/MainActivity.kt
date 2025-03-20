package io.sensify.sensor.ui

import android.content.Context
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.google.accompanist.pager.ExperimentalPagerApi
import io.sensify.sensor.domains.sensors.packets.SensorPacketsProvider
import io.sensify.sensor.domains.sensors.provider.SensorsProvider
import io.sensify.sensor.ui.navigation.NavGraphApp
import io.sensify.sensor.ui.resource.themes.SensifyM3Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        throw NumberFormatException("Test Crash") // Force a crash
        lifecycleScope.launch {
            SensorsProvider.getInstance().setSensorManager(sensorManager)
            SensorPacketsProvider.getInstance().setSensorManager(sensorManager)
            setContent {
                SensifyM3Theme {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        NavGraphApp()
                    }
                }
            }
        }


    }


    override fun onDestroy() {
        super.onDestroy()

        CoroutineScope(Dispatchers.Default).launch {
            SensorsProvider.getInstance().clearAll()
            SensorPacketsProvider.getInstance().clearAll()
        }

        lifecycleScope.launch {

        }
    }
}
