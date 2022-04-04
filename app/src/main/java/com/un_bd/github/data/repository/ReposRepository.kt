package com.un_bd.github.data.repository

import com.un_bd.github.model.ReposModel
import com.un_bd.github.net.GitHubApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReposRepository(private val gitHubApiService: GitHubApiService) {
  fun getRepos(user: String): Flow<List<ReposModel>> {
    return flow {
      emit(gitHubApiService.getRepos(user))
    }
  }
}

