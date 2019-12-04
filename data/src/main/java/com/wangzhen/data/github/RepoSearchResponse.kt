package com.wangzhen.data.github

import com.google.gson.annotations.SerializedName

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
data class RepoSearchResponse(
    @SerializedName("total_count")
    val total: Int = 0,
    @SerializedName("items")
    val items: List<Repo>
)
