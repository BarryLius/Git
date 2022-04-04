package com.un_bd.github.ui.widget

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState

@Composable
fun ListFirstLoadState(
  loadState: CombinedLoadStates,
  notLoading: @Composable () -> Unit,
  loading: @Composable () -> Unit,
  error: @Composable (error: String) -> Unit,
) {
  when (val result = loadState.refresh) {
    is LoadState.NotLoading -> notLoading()
    is LoadState.Error -> error(result.error.message.toString())
    LoadState.Loading -> loading()
    else -> {
      Log.e("else", ">>>")
    }
  }
}