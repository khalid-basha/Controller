package com.graduationProject2.controller

import com.google.gson.annotations.SerializedName

data class AllValues (
    @SerializedName("sp") var sp: Double? = null,
    @SerializedName("acceptedError") var acceptedError: Double? = null,
    @SerializedName("color") var color: Int? = null,
    @SerializedName("setValue") var setValue: Int? = null
)
