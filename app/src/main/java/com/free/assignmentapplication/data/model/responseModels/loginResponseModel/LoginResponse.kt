package com.free.assignmentapplication.data.model.responseModels.loginResponseModel

import com.free.assignmentapplication.data.model.responseModels.BaseResponseModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName("refresh_token")
    val refreshToken: String
):BaseResponseModel()
