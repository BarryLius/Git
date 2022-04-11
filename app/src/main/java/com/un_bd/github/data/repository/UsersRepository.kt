package com.un_bd.github.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.un_bd.github.data.paging.UsersPagingSource
import com.un_bd.github.model.UserModelX
import com.un_bd.github.api.GitHubService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsersRepository @Inject constructor(
  private val gitHubService: GitHubService
) {
  fun getUsers(since: Int = 0): Flow<PagingData<UserModelX>> {
    return Pager(config = PagingConfig(pageSize = 30)) {
      UsersPagingSource(gitHubService, since)
    }.flow
  }
}

