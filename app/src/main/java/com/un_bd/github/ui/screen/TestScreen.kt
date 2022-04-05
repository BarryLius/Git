package com.un_bd.github.ui.screen

import android.annotation.SuppressLint
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.VectorConverter
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.math.roundToInt


@Preview(showBackground = true)
@Composable
fun Test() {
  TopAppBar(title = { Text(text = "GitHub") })
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun TestScreen() {
  val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
  Box(
    modifier = Modifier
      .size(50.dp)
      .background(Color.Red)
      .pointerInput(Unit) {
        coroutineScope {
          while (true) {
            // Detect a tap event and obtain its position.
            val position = awaitPointerEventScope {
              awaitFirstDown().position
            }
            launch {
              // Animate to the tap position.
              offset.animateTo(position)
            }
          }
        }
      }
  ) {
    Box(modifier = Modifier.offset { offset.value.toIntOffset() })
  }
}

private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())