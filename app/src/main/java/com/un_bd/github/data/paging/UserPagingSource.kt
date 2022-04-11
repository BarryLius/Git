package com.un_bd.github.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.un_bd.github.model.UserModelX
import com.un_bd.github.api.GitHubService

class UsersPagingSource(
  private val gitHubService: GitHubService,
  private val pageNum: Int = 0
) : PagingSource<Int, UserModelX>() {
  override fun getRefreshKey(state: PagingState<Int, UserModelX>): Int? {
    return state.anchorPosition
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UserModelX> {
    return try {
      val currentPage = params.key ?: pageNum
      val users = gitHubService.getUsers(currentPage)

      LoadResult.Page(
        data = users,
        prevKey = if (currentPage == pageNum) null else currentPage - 1,
        nextKey = if (users.isNullOrEmpty()) null else currentPage + 1
      )
    } catch (e: Exception) {
      LoadResult.Error(e)
    }
  }
}