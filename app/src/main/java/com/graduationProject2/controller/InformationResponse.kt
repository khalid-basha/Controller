package com.graduationProject2.controller

import com.google.gson.annotations.SerializedName

data class InformationResponse(
    @SerializedName("sp"            ) var sp            : Double? = null,
    @SerializedName("act"           ) var act           : Double? = null,
    @SerializedName("setValue"      ) var setValue      : Int?    = null,
    @SerializedName("acceptedError" ) var acceptedError : Double? = null,
    @SerializedName("light"         ) var light         : Int? = null
)
