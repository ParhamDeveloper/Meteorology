package com.parhambaghebani.model

import androidx.lifecycle.MutableLiveData
import com.parhambaghebani.utility.ApiManager
import com.parhambaghebani.utility.Response
import com.parhambaghebani.utility.model.AirTemperatureModel
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiManager: ApiManager) {
    val data = MutableLiveData<Response<AirTemperatureModel?>>()

    fun onMapClick(latitude: Double, longitude: Double) {
        data.postValue(Response.loading())
        val response = apiManager.getAirTemperature(latitude, longitude)
        data.postValue(response)
    }
}