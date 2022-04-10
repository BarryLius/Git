package com.un_bd.github.data.repository

import com.un_bd.github.model.ReposModel
import com.un_bd.github.net.GitHubService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ReposRepository @Inject constructor(
  private val gitHubService: GitHubService
) {
  fun getRepos(user: String): Flow<List<ReposModel>> {
    return flow {
      emit(gitHubService.getRepos(user))
    }
  }
}

