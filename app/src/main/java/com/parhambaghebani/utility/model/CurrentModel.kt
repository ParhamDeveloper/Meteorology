package com.parhambaghebani.utility.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class CurrentModel(
    @SerialName("temp")
    val temp: Float
)