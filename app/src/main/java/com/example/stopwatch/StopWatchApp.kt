package com.example.stopwatch

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.app.Application

sealed interface StopWatchEvent
{
    object onStart: StopWatchEvent
    object onStop: StopWatchEvent
    object onReset: StopWatchEvent
}

class StopWatchApp: Application() {

    val stopWatchView = StopWatchView()

}

class StopWatchView: ViewModel()
{
    var hours = mutableStateOf(0)
        private set
    var minutes = mutableStateOf(0)
        private set
    var seconds = mutableStateOf(0)
        private set
    var milliseconds = mutableStateOf(0)
        private set
    private var isCounting = false

    init
    {   viewModelScope.launch(Dispatchers.IO)
        {
            while(true)
            {   if (isCounting)
                {   if (milliseconds.value < 99)
                    {       milliseconds.value++    }
                    else
                    {   if (seconds.value < 59)
                        {   seconds.value++
                            milliseconds.value = 0
                        }
                        else {
                            if (minutes.value < 59) {
                                minutes.value++
                                seconds.value = 0
                                milliseconds.value = 0
                            }
                            else {
                                if (hours.value < 24) {
                                    hours.value++
                                    minutes.value = 0
                                    seconds.value = 0
                                    milliseconds.value = 0
                                } else {
                                    hours.value = 0
                                    minutes.value = 0
                                    seconds.value = 0
                                    milliseconds.value = 0
                                }
                            }
                        }
                    }
                }
                delay(7)
            }
        }
    }

    fun onEvent(event: StopWatchEvent)
    {   when(event)
        {   StopWatchEvent.onReset ->
            {
                isCounting = false
                hours.value = 0
                minutes.value = 0
                seconds.value = 0
                milliseconds.value = 0
            }
            StopWatchEvent.onStart ->
            {   isCounting = true
            }
            StopWatchEvent.onStop ->
            {   isCounting = false
            }
        }
    }
}