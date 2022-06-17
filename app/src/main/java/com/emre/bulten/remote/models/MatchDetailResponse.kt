package com.emre.bulten.remote.models

import com.google.gson.annotations.SerializedName


data class MatchDetailResponse(
    @SerializedName("Detail") var Detail: Detail? = null

)

data class Detail(
    @SerializedName("_id")
    val _id: ID? = null,
    @SerializedName("HomeTeam")
    val homeTeam: Team? = null,
    @SerializedName("AwayTeam")
    val awayTeam: Team? = null
)

data class ID(
    @SerializedName("CurrentMatch")
    val currentMatch: CurrentMatch? = null,
)

data class CurrentMatch(
    @SerializedName("RoundDetail")
    val roundDetail: String? = null,
    @SerializedName("DateOfMatch")
    val dateOfMatch: String? = null,
    @SerializedName("League")
    val league: String? = null,
    @SerializedName("HTResult")
    val htResult: String? = null,
    @SerializedName("FTResult")
    val ftResult: String? = null,
    @SerializedName("HomeTeam")
    val homeTeam: Team? = null,
    @SerializedName("AwayTeam")
    val awayTeam: Team? = null
)

data class Team(
    @SerializedName("NesineNameLong")
    val name: String? = null,
    @SerializedName("LastMatches")
    val lastMatches: List<CurrentMatch>? = null
)
