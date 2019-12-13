package com.wangzhen.baselib.http.service

import androidx.lifecycle.LiveData
import com.wangzhen.baselib.http.ApiResponse
import com.wangzhen.baselib.http.mock.Mock
import com.wangzhen.data.github.RepoSearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubService {
    @Mock(assets = "github.json")
    @GET("search/repositories")
    fun searchRepos(@Query("q") query: String): LiveData<ApiResponse<RepoSearchResponse>>
}
