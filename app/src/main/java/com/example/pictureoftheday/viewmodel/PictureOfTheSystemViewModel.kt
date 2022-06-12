package com.example.pictureoftheday.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.repository.api.PictureOfTheNasaRetrofitImpl
import com.example.pictureoftheday.repository.systemsolar.PictureOfTheDaySystemResponseDate
import com.example.pictureoftheday.view.PictureOfTheSystemFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheSystemViewModel (
    private val liveData: MutableLiveData<PictureOfTheSystemAppState> = MutableLiveData(),
    private val pictureOfTheNasaRetrofitImpl: PictureOfTheNasaRetrofitImpl = PictureOfTheNasaRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheSystemAppState> {
        return liveData
    }

    fun sendRequest(
        callbachFragment: PictureOfTheSystemFragment.CallbackFragment,
        selectionDate: Int = 1
    ) {
        liveData.postValue(PictureOfTheSystemAppState.Loading(null))
        // TODO HW а есть ли вообще BuildConfig.NASA_API_KEY

        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            callbachFragment.onError("Не найден API key сайта nasa.gov.com")
            liveData.postValue(PictureOfTheSystemAppState.Error("Ошибка загрузки. Пробуйте снова"))
            return
        }
        when (selectionDate) {
            1 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,0) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheSystem(BuildConfig.NASA_API_KEY)
                    .enqueue(callback)
            }
            2 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,-1) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheSystem(BuildConfig.NASA_API_KEY)
                    .enqueue(callback)
            }
            3 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,-2) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheSystem(BuildConfig.NASA_API_KEY)
                    .enqueue(callback)
            }
            else -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,0) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)
                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheSystem(BuildConfig.NASA_API_KEY)
                    .enqueue(callback)
            }
        }
    }


    private val callback = object : Callback<PictureOfTheDaySystemResponseDate> {
        override fun onResponse(
            call: Call<PictureOfTheDaySystemResponseDate>,
            response: Response<PictureOfTheDaySystemResponseDate>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheSystemAppState.Success(it))
                }
            } else {
                liveData.postValue(PictureOfTheSystemAppState.Error("Ошибка загрузки. Пробуйте снова"))
                Log.d("@@@", "onResponse: ".plus(response.errorBody().toString()))
            }

        }

        override fun onFailure(call: Call<PictureOfTheDaySystemResponseDate>, t: Throwable) {
            liveData.postValue(PictureOfTheSystemAppState.Error(t.message.toString()))
        }
    }

    fun sendRequestToday(callbachFragment: PictureOfTheSystemFragment.CallbackFragment) {
        sendRequest(callbachFragment, 1)
    }

    fun sendRequestYT(callbachFragment: PictureOfTheSystemFragment.CallbackFragment) {
        sendRequest(callbachFragment, 2)
    }

    fun sendRequestTDBY(callbachFragment: PictureOfTheSystemFragment.CallbackFragment) {
        sendRequest(callbachFragment, 3)
    }

}