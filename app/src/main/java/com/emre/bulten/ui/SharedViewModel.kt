package com.emre.bulten.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emre.bulten.remote.BultenAPI
import com.emre.bulten.remote.models.MatchDetailResponse
import com.emre.bulten.remote.models.NoticesResponse
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SharedViewModel : ViewModel() {

    private val _noticesResponse: MutableLiveData<NoticesResponse> = MutableLiveData()
    val noticesResponse: LiveData<NoticesResponse> = _noticesResponse

    private val _detailResponse: MutableLiveData<MatchDetailResponse> = MutableLiveData()
    val detailResponse: LiveData<MatchDetailResponse> = _detailResponse

    init {
        pullNotices()
    }

    private fun pullNotices(): LiveData<NoticesResponse> {

        BultenAPI.createBulten().getNotices().enqueue(object : Callback<NoticesResponse> {
            override fun onResponse(
                call: Call<NoticesResponse>,
                response: Response<NoticesResponse>
            ) {
                if (response.isSuccessful) {
                    _noticesResponse.postValue(response.body())
                    //_noticesResponse.value = response.body()
                }
            }

            override fun onFailure(call: Call<NoticesResponse>, t: Throwable) {
                t.printStackTrace()
            }

        })

        return _noticesResponse
    }

    fun getMatchDetail(matchCode: String): LiveData<MatchDetailResponse> {

        BultenAPI.createIstatistik().getDetail(matchCode).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.isSuccessful) {

                    val stringToMatchDetailModel = Gson().fromJson(
                        JSONObject(response.body().toString()).get("Data").toString(),
                        MatchDetailResponse::class.java
                    )

                    _detailResponse.postValue(stringToMatchDetailModel)

                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                t.printStackTrace()
            }

        })

        return _detailResponse

    }

}
