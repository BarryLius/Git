package com.un_bd.github.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.un_bd.github.data.repository.UsersRepository
import com.un_bd.github.model.UserModelX
import com.un_bd.github.net.ApiClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class UsersViewModel(private val usersRepository: UsersRepository) : ViewModel() {
  private val user = MutableStateFlow<PagingData<UserModelX>>(PagingData.empty())
  var uiState by mutableStateOf(UsersUiState())
    private set

  init {
    getUsers()
  }

  private fun getUsers() {
    viewModelScope.launch {
      usersRepository.getUsers2().cachedIn(viewModelScope)
        .collect {
          user.value = it
          uiState = uiState.copy(list = user)
        }
    }
  }

  @Suppress("UNCHECKED_CAST")
  companion object {
    fun provideFactory(
      usersRepository: UsersRepository = UsersRepository(ApiClient.gitHubApiService)
    ): ViewModelProvider.Factory =
      object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
          return UsersViewModel(usersRepository) as T
        }
      }
  }
}

data class UsersUiState(
  val list: Flow<PagingData<UserModelX>> = MutableStateFlow(PagingData.empty())
)