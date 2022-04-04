package com.un_bd.github.ui.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.un_bd.github.ui.theme.GitHubTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PageLoading(loadMsg: String = "Loading...") {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxSize()
  ) {
    CircularProgressIndicator()
    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    Text(text = loadMsg, style = MaterialTheme.typography.body1)
  }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PageEmpty(emptyMsg: String = "Data Is Empty!") {
  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
    Text(text = emptyMsg, style = MaterialTheme.typography.h6)
  }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PageRetry(errMsg: String = "ERROR", onRetryClick: () -> Unit = { }) {
  Column(
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center,
    modifier = Modifier.fillMaxSize()
  ) {
    Text(
      text = "ERROR\t:\t$errMsg",
      style = MaterialTheme.typography.h6,
      color = MaterialTheme.colors.error
    )
    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    OutlinedButton(onClick = onRetryClick) {
      Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
      Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
      Text(text = "RETRY")
    }
  }
}