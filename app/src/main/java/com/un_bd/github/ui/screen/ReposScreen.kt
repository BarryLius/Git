package com.un_bd.github.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.un_bd.github.model.ReposModel
import com.un_bd.github.ui.widget.PageEmpty
import com.un_bd.github.ui.widget.PageLoading
import com.un_bd.github.ui.widget.PageRetry
import com.un_bd.github.ui.widget.StateLayout
import com.un_bd.github.viewmodel.ReposUiAction
import com.un_bd.github.viewmodel.ReposViewModel

@Composable
fun ReposScreen(
  reposViewModel: ReposViewModel,
  user: String,
  onBack: () -> Unit = { }
) {
  Column {
    TopAppBar(title = { Text(text = user) },
      navigationIcon = {
        IconButton(onClick = { onBack() }) {
          Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
        }
      })
    StateLayout(pageStateData = reposViewModel.uiState.pageState,
      loading = { PageLoading() },
      empty = { PageEmpty() },
      error = {
        PageRetry(errMsg = reposViewModel.uiState.error?.message.toString()) {
          reposViewModel.getHandleAction(ReposUiAction.Retry)
        }
      },
      content = { ReposList(reposViewModel.uiState.list) }
    )
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReposList(items: List<ReposModel>) {
  LazyColumn {
    items(items = items) {
      ListItem(
        modifier = Modifier.clickable { },
        text = { Text(text = it.name.toString()) },
        secondaryText = { Text(text = it.description.toString()) },
        overlineText = { Text(text = "${it.language.toString()}-${it.pushedAt.toString()}") }
      )
      Divider(modifier = Modifier.padding(start = 16.dp, end = 16.dp))
    }
  }
}

@Preview(showSystemUi = true)
@Composable
fun DebugTip() {
  Row(
    horizontalArrangement = Arrangement.End
  ) {
    val name = object : Any() {}.javaClass.enclosingMethod?.name
    Text(
      text = name.toString(),
      color = Color.White,
      modifier = Modifier
        .background(Color.Red.copy(alpha = 0.68f))
        .padding(top = 2.dp, bottom = 2.dp, start = 8.dp, end = 8.dp)
    )
  }
}