package com.free.assignmentapplication.data.remote

import com.free.assignmentapplication.data.model.requestModels.loginRequestModel.LoginRequest
import com.free.assignmentapplication.data.model.requestModels.signupRequestModel.SignupRequest
import com.free.assignmentapplication.data.model.responseModels.loginResponseModel.LoginResponse
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface ApiService {

    @POST("users/")
    suspend fun signupUser(@Body signupRequest: SignupRequest): SignupResponse

    @POST("auth/login")
    suspend fun loginUser(@Body loginRequest: LoginRequest):LoginResponse

    @GET("products")
    suspend fun getProducts():List<ProductResponse>
}