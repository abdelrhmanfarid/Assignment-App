package com.free.assignmentapplication.data.remote

import com.free.assignmentapplication.data.model.responseModels.loginResponseModel.LoginResponse
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse


interface RemoteRepository {

    suspend fun signupUser(map: MutableMap<String, Any>): SignupResponse

    suspend fun loginUser(map: MutableMap<String, Any>): LoginResponse

    suspend fun getProducts(): List<ProductResponse>
}