package com.example.pictureoftheday.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseDate
import com.example.pictureoftheday.repository.api.PictureOfTheNasaRetrofitImpl
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthDate
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthListDateItem
import com.example.pictureoftheday.repository.earth.PictureOfTheEarthResponseListDate
import com.example.pictureoftheday.view.PictureOfTheEarthFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class PictureOfTheEarthViewModel(
    private val liveData: MutableLiveData<PictureOfTheEarthAppState> = MutableLiveData(),
    private val pictureOfTheNasaRetrofitImpl: PictureOfTheNasaRetrofitImpl = PictureOfTheNasaRetrofitImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<PictureOfTheEarthAppState> {
        return liveData
    }

    fun sendRequest(
        callbachFragment: PictureOfTheEarthFragment.CallbackFragment,
        selectionDate: Int = 1
    ) {
        liveData.postValue(PictureOfTheEarthAppState.Loading(null))
        // TODO HW а есть ли вообще BuildConfig.NASA_API_KEY

        if (BuildConfig.NASA_API_KEY.isNullOrEmpty()) {
            callbachFragment.onError("Не найден API key сайта nasa.gov.com")
            liveData.postValue(PictureOfTheEarthAppState.Error("Ошибка загрузки. Пробуйте снова"))
            return
        }
        when (selectionDate) {
            1 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,0) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)

                pictureOfTheNasaRetrofitImpl.getRetrofit()
                .getPictureOfTheEarth(BuildConfig.NASA_API_KEY,dateKey,-95.33,29.78,0.10)
                .enqueue(callback)
            }
            2 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,-1) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)

                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheEarth(BuildConfig.NASA_API_KEY,dateKey,-95.33,29.78,0.10)
                    .enqueue(callback)
            }
            3 -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,-2) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)

                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheEarth(BuildConfig.NASA_API_KEY,dateKey,-95.33,29.78,0.10)
                    .enqueue(callback)
            }
            else -> {
                val calendar = Calendar.getInstance()
                val today = calendar.apply { add(Calendar.DAY_OF_MONTH,0) }.time
                val dateKey = SimpleDateFormat("yyyy-MM-dd").format(today)

                pictureOfTheNasaRetrofitImpl.getRetrofit()
                    .getPictureOfTheEarth(BuildConfig.NASA_API_KEY,dateKey,-95.33,29.78,0.10)
                    .enqueue(callback)
            }
        }
    }


    private val callback = object : Callback<PictureOfTheEarthResponseDate> {
        override fun onResponse(
            call: Call<PictureOfTheEarthResponseDate>,
            response: Response<PictureOfTheEarthResponseDate>
        ) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(PictureOfTheEarthAppState.Success(it))
                }
            } else {
                liveData.postValue(PictureOfTheEarthAppState.Error("Ошибка загрузки. Пробуйте снова"))
                Log.d("@@@", "onResponse: ".plus(response.errorBody().toString()))
            }

        }

        override fun onFailure(call: Call<PictureOfTheEarthResponseDate>, t: Throwable) {
            liveData.postValue(PictureOfTheEarthAppState.Error(t.message.toString()))
        }

    }

    fun sendRequestToday(callbachFragment: PictureOfTheEarthFragment.CallbackFragment) {
        sendRequest(callbachFragment, 1)
    }

    fun sendRequestYT(callbachFragment: PictureOfTheEarthFragment.CallbackFragment) {
        sendRequest(callbachFragment, 2)
    }

    fun sendRequestTDBY(callbachFragment: PictureOfTheEarthFragment.CallbackFragment) {
        sendRequest(callbachFragment, 3)
    }

    interface CallbackListPicture{
        fun getListPicture(pictureOfTheEarthDate: PictureOfTheEarthDate)
    }

    var pictureListDate: PictureOfTheEarthDate = PictureOfTheEarthDate("",0.0,0.0)
    private val callbackListPicture = object :CallbackListPicture{
        override fun getListPicture(pictureOfTheEarthListDate: PictureOfTheEarthDate) {
            pictureListDate =  pictureOfTheEarthListDate
        }
    }
}