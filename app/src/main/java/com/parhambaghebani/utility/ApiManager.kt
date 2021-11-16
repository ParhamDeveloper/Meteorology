package com.parhambaghebani.utility

import com.parhambaghebani.utility.model.AirTemperatureModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import javax.inject.Inject

class ApiManager @Inject constructor(private val okHttpClient: OkHttpClient, private val jsonParser: Json) {

    fun getAirTemperature(latitude: Double, longitude: Double): Response<AirTemperatureModel?> {
        val url = String.format(Locale.US, "https://api.openweathermap.org/data/2.5/onecall?lat=%s&lon=%s&appid=c02c53810c03d0d4fcd6d74658d27263", latitude.toString(), longitude.toString())
        val ret: Response<AirTemperatureModel?> = try {
            val response: okhttp3.Response = callApi(url)
            val resultParsed: AirTemperatureModel? = response.body?.let { body ->
                jsonParser.decodeFromString(body.string())
            }
            if (response.isSuccessful) {
                Response.success(resultParsed)
            } else {
                Response.error()
            }
        } catch (e: Exception) {
            Response.error()
        }
        return ret
    }

    private fun callApi(url: String): okhttp3.Response {
        val request: Request = Request.Builder().url(url).build()
        return okHttpClient.newCall(request).execute()
    }

}