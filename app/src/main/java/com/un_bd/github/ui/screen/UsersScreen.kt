package com.un_bd.github.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.un_bd.github.model.UserModelX
import com.un_bd.github.ui.theme.GitHubTheme
import com.un_bd.github.ui.widget.*
import com.un_bd.github.viewmodel.UsersViewModel

@Composable
fun UsersScreen(
  usersViewModel: UsersViewModel,
  onItemClick: (user: String) -> Unit = { }
) {
  val users = usersViewModel.uiState.list.collectAsLazyPagingItems()
  Log.e("users", ">>>${users.itemCount}")

  Column {
    TopAppBar(title = { Text(text = "GitHub") })
    ListFirstLoadState(
      loadState = users.loadState,
      notLoading = { UserList(users, onItemClick) },
      loading = { PageLoading() },
      error = { PageRetry(it) { users.refresh() } }
    )
  }
}

@Composable
fun UserList(
  items: LazyPagingItems<UserModelX>,
  onItemClick: (user: String) -> Unit = { }
) {
  if (items.itemCount > 0) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      userItem(items, onItemClick)
      listBottomState(items = items)
    }
  } else {
    PageEmpty()
  }
}

@OptIn(ExperimentalMaterialApi::class)
fun LazyListScope.userItem(
  items: LazyPagingItems<UserModelX>,
  onItemClick: (user: String) -> Unit = { }
) {
  items(items = items) {
    ListItem(
      icon = {
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current).data(it?.avatarUrl.toString()).build(),
          placeholder = ColorPainter(MaterialTheme.colors.secondary),
          contentDescription = null,
          modifier = Modifier
            .clip(CircleShape)
            .size(56.dp)
        )
      },
      text = { Text(text = it?.login.toString()) },
      secondaryText = { Text(text = it?.url.toString()) },
      modifier = Modifier.clickable {
        onItemClick(it?.login.toString())
      }
    )
    Divider(modifier = Modifier.padding(start = 56.dp + 16.dp + 16.dp))
  }
}

@Composable
fun DefaultPreview() {
  GitHubTheme {
    // SuccessView(test)
  }
}