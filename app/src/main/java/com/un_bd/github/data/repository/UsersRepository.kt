package com.un_bd.github.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.un_bd.github.data.paging.UsersPagingSource
import com.un_bd.github.model.UserModelX
import com.un_bd.github.net.GitHubApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UsersRepository(private val gitHubApiService: GitHubApiService) {
  fun getUsers(since: Int = 0): Flow<List<UserModelX>> {
    return flow {
      emit(gitHubApiService.getUsers(since = since))
    }
  }

  fun getUsers2(since: Int = 0): Flow<PagingData<UserModelX>> {
    return Pager(config = PagingConfig(pageSize = 30)) {
      UsersPagingSource(gitHubApiService, since)
    }.flow
  }
}

