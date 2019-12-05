package com.wangzhen.androidcomponent.test.http.reps

import androidx.lifecycle.LiveData
import com.wangzhen.baselib.http.ApiClient
import com.wangzhen.baselib.http.ApiResponse
import com.wangzhen.baselib.http.Resource
import com.wangzhen.baselib.http.remote.RemoteOnlyResource
import com.wangzhen.data.github.RepoSearchResponse

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class GithubRepository {
    private val searchResource = object : RemoteOnlyResource<String, RepoSearchResponse>() {

        override fun createCall(params: String?): LiveData<ApiResponse<RepoSearchResponse>> {
            return ApiClient.getInstance().githubService.searchRepos(params ?: "")
        }
    }

    fun getSearchLiveData(): LiveData<Resource<RepoSearchResponse>> {
        return searchResource.asLiveData()
    }

    fun doSearch(query: String) {
        searchResource.startLoad(query, getSearchLiveData().value?.data)
    }
}
