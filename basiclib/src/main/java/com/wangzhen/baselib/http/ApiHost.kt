package com.wangzhen.baselib.http

object ApiHost {
    private const val RELEASE_GITHUB_HOST = "https://api.github.com/"

    fun getGithubHost(): String {
        return RELEASE_GITHUB_HOST
    }
}
