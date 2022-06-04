package com.example.pictureoftheday.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.repository.ErrorLoading
import com.example.pictureoftheday.repository.PictureOfTheDayResponseDate
import com.example.pictureoftheday.repository.PictureOfTheDayRetrofitImpl
import com.example.pictureoftheday.repository.PictureOfTheNasaRetrofitImpl
import com.example.pictureoftheday.view.PictureOfTheDayFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.Error
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheDayViewModel(
    private val liveData: MutableLiveData<PictureOfTheDayAppState> = MutableLiveData(),
    private val pictureOfTheDayRetrofitImpl: PictureOfTheDayRetrofitImpl = PictureOfTheDayRetrofitImpl(),
    private val pictureOfTheNasaRetrofitImpl: PictureOfTheNasaRetrofitImpl = PictureOfTheNasaRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheDayAppState> {
        return liveData
    }

    fun sendRequest(
        callbachFragment: PictureOfTheDayFragment.CallbackFragment,
        selectionDate: Int = 1
    ) {
        liveData.postValue(PictureOfTheDayAppState.Loading(null))
        // TODO HW а есть ли вообще BuildConfig.NASA_API_KEY

        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            callbachFragment.onError("Не найден API key сайта nasa.gov.com")
            liveData.postValue(PictureOfTheDayAppState.Error("Ошибка загрузки. Пробуйте снова"))
            return
        }
        when (selectionDate) {
            1 -> pictureOfTheDayRetrofitImpl.getRetrofit()
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY)
                .enqueue(callback)
            2 -> {
                val calendar = Calendar.getInstance()
                val yesterday = calendar.apply { add(Calendar.DAY_OF_MONTH, -1) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(yesterday)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheYesterday(BuildConfig.NASA_API_KEY, dateKey)
                    .enqueue(callback)
            }
            3 -> {
                val calendar = Calendar.getInstance()
                val afteYesterday = calendar.apply { add(Calendar.DAY_OF_MONTH, -2) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(afteYesterday)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheYesterday(BuildConfig.NASA_API_KEY, dateKey)
                    .enqueue(callback)
/*
                val calendar = Calendar.getInstance()
                val endDateKey= SimpleDateFormat("yyyy-MM-dd").format(calendar.time)
                val startDateKey = SimpleDateFormat("yyyy-MM-dd").format(calendar.apply {
                    add(
                        Calendar.DAY_OF_MONTH,
                        -1
                    )
                }.time)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfThePeriod(BuildConfig.NASA_API_KEY, startDateKey, endDateKey)
                    .enqueue(callback)
*/
            }
            else -> pictureOfTheDayRetrofitImpl.getRetrofit()
                .getPictureOfTheDay(BuildConfig.NASA_API_KEY)
                .enqueue(callback)
        }
    }


    private val callback = object : Callback<PictureOfTheDayResponseDate> {
        override fun onResponse(
            call: Call<PictureOfTheDayResponseDate>,
            response: Response<PictureOfTheDayResponseDate>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheDayAppState.Success(it))
                }
            } else {
                liveData.postValue(PictureOfTheDayAppState.Error("Ошибка загрузки. Пробуйте снова"))
                Log.d("@@@", "onResponse: ".plus(response.errorBody().toString()))
            }

        }

        override fun onFailure(call: Call<PictureOfTheDayResponseDate>, t: Throwable) {
            liveData.postValue(PictureOfTheDayAppState.Error(t.message.toString()))
        }

    }

    fun sendRequestToday(callbachFragment: PictureOfTheDayFragment.CallbackFragment) {
        sendRequest(callbachFragment, 1)
    }

    fun sendRequestYT(callbachFragment: PictureOfTheDayFragment.CallbackFragment) {
        sendRequest(callbachFragment, 2)
    }

    fun sendRequestTDBY(callbachFragment: PictureOfTheDayFragment.CallbackFragment) {
        sendRequest(callbachFragment, 3)
    }


}