package com.wangzhen.androidcomponent.test.http.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.wangzhen.androidcomponent.test.http.reps.GithubRepository
import com.wangzhen.baselib.http.Resource
import com.wangzhen.data.github.RepoSearchResponse

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
class GithubViewModel constructor(private val repository: GithubRepository) : ViewModel() {
    val reposLiveData: LiveData<Resource<RepoSearchResponse>> = repository.getSearchLiveData()

    fun startSearch(query: String) {
        repository.doSearch(query)
    }
}
