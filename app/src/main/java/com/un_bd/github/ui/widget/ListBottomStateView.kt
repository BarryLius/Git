package com.un_bd.github.ui.widget

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import java.lang.Thread.sleep
import kotlin.concurrent.thread

fun <T : Any> LazyListScope.listBottomState(items: LazyPagingItems<T>) {
  when (items.loadState.append) {
    is LoadState.NotLoading -> item { ListBottomNoMore() }
    LoadState.Loading -> item { ListBottomLoading() }
    is LoadState.Error -> item {
      ListBottomError {
        items.retry()
      }
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ListBottomLoading() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxWidth()
      .padding(12.dp)
  ) {
    CircularProgressIndicator()
  }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ListBottomError(onRetryClick: () -> Unit = { }) {

  // val infiniteTransition = rememberInfiniteTransition()
  // val size by infiniteTransition.animateFloat(
  //   initialValue = 0f,
  //   targetValue = 360f,
  //   animationSpec = infiniteRepeatable(
  //     animation = tween(1000)
  //   )
  // )

  var enable by remember { mutableStateOf(true) }
  val animFloat by animateFloatAsState(
    targetValue = if (enable) 360f else 0f,
    TweenSpec(2000)
  )

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clickable {
        enable = !enable
        onRetryClick()
      },
    contentAlignment = Alignment.Center
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = "ERROR ! Please Click This Retry Request ",
        Modifier.padding(top = 24.dp, bottom = 24.dp)
      )
      Spacer(modifier = Modifier.size(8.dp))
      Icon(
        imageVector = Icons.Default.Refresh,
        modifier = Modifier
          .rotate(animFloat),
        contentDescription = null
      )
    }
  }
}

@Preview(showBackground = true)
@Composable
fun ListBottomNoMore() {
  Box(
    contentAlignment = Alignment.Center,
    modifier = Modifier
      .fillMaxWidth()
      .padding(24.dp)
  ) {
    Text(text = "No More Data")
  }
}