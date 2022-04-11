package com.un_bd.github.api

import com.un_bd.github.model.ReposModel
import com.un_bd.github.model.UserModelX
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
  companion object {
    const val BASE_URL = "https://api.github.com/"
  }

  @GET(value = "users")
  suspend fun getUsers(@Query(value = "since") since: Int = 0): List<UserModelX>

  @GET(value = "users/{user}/repos")
  suspend fun getRepos(@Path(value = "user") User: String): List<ReposModel>
}