package com.example.pictureoftheday.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.repository.api.PictureOfTheNasaRetrofitImpl
import com.example.pictureoftheday.repository.mars.PictureOfTheMarsResponseDate
import com.example.pictureoftheday.view.PictureOfTheMarsFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheMarsViewModel(
    private val liveData: MutableLiveData<PictureOfTheMarsAppState> = MutableLiveData(),
    private val pictureOfTheNasaRetrofitImpl: PictureOfTheNasaRetrofitImpl = PictureOfTheNasaRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheMarsAppState> {
        return liveData
    }

    fun sendRequest(
        callbachFragment: PictureOfTheMarsFragment.CallbackOnError,
        selectionDate: Int = 1
    ) {
        liveData.postValue(PictureOfTheMarsAppState.Loading(null))
        // TODO HW а есть ли вообще BuildConfig.NASA_API_KEY

        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            callbachFragment.onError("Не найден API key сайта nasa.gov.com")
            liveData.postValue(PictureOfTheMarsAppState.Error("Ошибка загрузки. Пробуйте снова"))
            return
        }
        when (selectionDate) {
            1 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,0) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheMars(BuildConfig.NASA_API_KEY,1000,1,"MAST")
                    .enqueue(callback)
            }
            2 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,-1) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheMars(BuildConfig.NASA_API_KEY,1000,1,"FHAZ")
                    .enqueue(callback)
            }
            3 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,-2) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheMars(BuildConfig.NASA_API_KEY,1000,1, "RHAZ")
                    .enqueue(callback)
            }
            else -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,0) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheMars(BuildConfig.NASA_API_KEY,1000,1,"MAST")
                    .enqueue(callback)
            }
        }
    }


    private val callback = object : Callback<PictureOfTheMarsResponseDate> {
        override fun onResponse(
            call: Call<PictureOfTheMarsResponseDate>,
            response: Response<PictureOfTheMarsResponseDate>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheMarsAppState.Success(it))
                }
            } else {
                liveData.postValue(PictureOfTheMarsAppState.Error("Ошибка загрузки. Пробуйте снова"))
                Log.d("@@@", "onResponse: ".plus(response.errorBody().toString()))
            }

        }

        override fun onFailure(call: Call<PictureOfTheMarsResponseDate>, t: Throwable) {
            liveData.postValue(PictureOfTheMarsAppState.Error(t.message.toString()))
        }

    }

    fun sendRequestToday(callbackOnError: PictureOfTheMarsFragment.CallbackOnError) {
        sendRequest(callbackOnError, 1)
    }

    fun sendRequestYT(callbackOnError: PictureOfTheMarsFragment.CallbackOnError) {
        sendRequest(callbackOnError, 2)
    }

    fun sendRequestTDBY(callbackOnError: PictureOfTheMarsFragment.CallbackOnError) {
        sendRequest(callbackOnError, 3)
    }

}