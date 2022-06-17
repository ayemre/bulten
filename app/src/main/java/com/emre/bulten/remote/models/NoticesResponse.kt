package com.emre.bulten.remote.models

import com.google.gson.annotations.SerializedName


data class NoticesResponse(
    @SerializedName("sg")
    val notice: Notice? = null
)

data class Notice(
    @SerializedName("EA")
    val matchList: List<Match>? = null
)

data class Match(
    @SerializedName("C")
    val matchCode: String,
    @SerializedName("HN")
    val homeTeam: String,
    @SerializedName("AN")
    val awayTeam: String,
    @SerializedName("D")
    val date: String,
    @SerializedName("DAY")
    val day: String,
    @SerializedName("T")
    val time: String,
    /**
     * futbol maçlarının tipi = 1
     */
    @SerializedName("TYPE")
    val type: Int,
) {
    var isFavorite = false
}
