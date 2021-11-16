package com.parhambaghebani.utility.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class AirTemperatureModel(
    @SerialName("lat")
    val lat: Double,
    @SerialName("lon")
    val lon: Double,
    @SerialName("current")
    val current: CurrentModel
)