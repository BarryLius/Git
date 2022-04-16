package com.un_bd.github.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.un_bd.github.data.repository.ReposRepository
import com.un_bd.github.model.ReposModel
import com.un_bd.github.ui.widget.PageState
import com.un_bd.github.ui.widget.PageStateData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReposViewModel @Inject constructor(
  private val savedStateHandle: SavedStateHandle,
  private val reposRepository: ReposRepository
) : ViewModel() {
  private var user: String = ""

  var uiState by mutableStateOf(ReposUiState())
    private set

  init {
    user = savedStateHandle.get<String>("user") ?: ""
    uiState = uiState.copy(user = user)
    getRepose()
  }

  fun getHandleAction(uiAction: ReposUiAction) {
    when (uiAction) {
      ReposUiAction.Retry -> getRepose()
    }
  }

  private fun getRepose() {
    viewModelScope.launch {
      reposRepository.getRepos(user)
        .onStart {
          uiState = uiState.copy(pageState = PageStateData(PageState.LOADING))
        }
        .catch {
          uiState = if (it.message.toString().contains("HTTP 404")) {
            uiState.copy(pageState = PageStateData(PageState.EMPTY))
          } else {
            uiState.copy(pageState = PageStateData(PageState.ERROR), error = it)
          }
        }
        .map {
          it.sortedByDescending { list ->
            list.pushedAt
          }
        }
        .collect {
          uiState = if (it.isNullOrEmpty()) {
            uiState.copy(pageState = PageStateData(PageState.EMPTY))
          } else {
            uiState.copy(pageState = PageStateData(PageState.CONTENT), list = it)
          }
        }
    }
  }
}

data class ReposUiState(
  val user: String = "",
  val pageState: PageStateData = PageStateData(PageState.LOADING),
  val list: List<ReposModel> = emptyList(),
  val error: Throwable? = null
)

sealed class ReposUiAction {
  object Retry : ReposUiAction()
}