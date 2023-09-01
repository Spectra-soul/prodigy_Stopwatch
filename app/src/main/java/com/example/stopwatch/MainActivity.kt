package com.example.stopwatch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stopwatch.ui.theme.StopWatchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StopWatchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary
                ){
                    StopWatchScreen((application as StopWatchApp).stopWatchView)
                }
            }
        }
    }
}

@Composable
fun StopWatchScreen(viewModel: StopWatchView){
    val darkModeEnabled by LocalTheme.current.darkMode.collectAsState()
    val textColor = if (darkModeEnabled) Color.White else Color.Black
    val themeStopwatch = LocalTheme.current

    Box(
        modifier = Modifier.fillMaxWidth().fillMaxHeight().padding(15.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(75.dp),
            modifier = Modifier.wrapContentWidth().wrapContentHeight()
        ) {
            Column {
                Row(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight()
                        .align(Alignment.CenterHorizontally).padding(2.dp)
                ) {
                    Text(
                        text = if(viewModel.hours.value<10) "0${viewModel.hours.value}"
                                else viewModel.hours.value.toString(),
                        color = textColor,
                        style = TextStyle(fontSize = 48.sp, textAlign = TextAlign.End, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth().padding(4.dp)
                    )
                    Text(
                        text = "\n\n\nH  ",
                        color = textColor,
                        style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = if(viewModel.minutes.value<10) "0${viewModel.minutes.value}"
                                else viewModel.minutes.value.toString(),
                        color = textColor,
                        style = TextStyle(fontSize = 48.sp, textAlign = TextAlign.End, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth().padding(4.dp)
                    )
                    Text(
                        text = "\n\n\nM  ",
                        color = textColor,
                        style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = if(viewModel.seconds.value<10) "0${viewModel.seconds.value}"
                                else viewModel.seconds.value.toString(),
                        color = textColor,
                        style = TextStyle(fontSize = 48.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth().padding(4.dp)
                    )
                    Text(
                        text = "\n\n\nS  ",
                        color = textColor,
                        style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth()
                    )
                    Text(
                        text = "\n" +
                                if(viewModel.milliseconds.value<10) "0${viewModel.milliseconds.value}"
                                else viewModel.milliseconds.value.toString(),
                        color = textColor,
                        style = TextStyle(fontSize = 23.sp, textAlign = TextAlign.Start, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth().padding(4.dp)
                    )
                    Text(
                        text = "\n\n\nms  ",
                        color = textColor,
                        style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                        modifier = Modifier.wrapContentWidth()
                    )
                }

                Row(
                    modifier = Modifier.padding(horizontal = 30.dp, vertical = 15.dp)
                ) {
                    Button(
                        onClick = {viewModel.onEvent(StopWatchEvent.onStart)},
                        modifier = Modifier.weight(1F).padding(4.dp)
                    ) {
                        Text("Start", color = textColor)
                    }

                    Button(
                        onClick = {viewModel.onEvent(StopWatchEvent.onStop)},
                        modifier = Modifier.weight(1F).padding(4.dp)
                    ) {
                        Text("Stop", color = textColor)
                    }

                    Button(
                        onClick = {viewModel.onEvent(StopWatchEvent.onReset)},
                        modifier = Modifier.weight(1F).padding(4.dp)
                    ) {
                        Text("Reset", color = textColor)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.clip(RoundedCornerShape(bottomEnd= 15.dp, bottomStart= 15.dp))
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 20.dp, vertical = 5.dp)
        ) {
            Text(
                text = "StopWatch",
                style = TextStyle(fontSize = 16.sp, textAlign = TextAlign.Center, fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(horizontal = 8.dp).clickable
                {
                    themeStopwatch.toggleTheme()
                },
                color = textColor
            )
        }
    }
}


@Preview()
@Composable
fun StopWatchScreenPreview() {
    StopWatchTheme {
        StopWatchScreen(StopWatchView())
    }
}
