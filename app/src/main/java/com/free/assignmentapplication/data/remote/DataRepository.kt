package com.free.assignmentapplication.data.remote


import com.free.assignmentapplication.data.model.requestModels.loginRequestModel.LoginRequest
import com.free.assignmentapplication.data.model.requestModels.signupRequestModel.SignupRequest
import com.free.assignmentapplication.data.model.responseModels.loginResponseModel.LoginResponse
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse
import com.free.assignmentapplication.data.remote.MyRequestMap.LOGIN_USER
import com.free.assignmentapplication.data.remote.MyRequestMap.SIGNUP_USER
import javax.inject.Inject

class DataRepository @Inject constructor(private val apiService: ApiService) : RemoteRepository {

    override suspend fun signupUser(map: MutableMap<String, Any>): SignupResponse {
        return apiService.signupUser(map[SIGNUP_USER] as SignupRequest)
    }

    override suspend fun loginUser(map: MutableMap<String, Any>): LoginResponse {
        return apiService.loginUser(map[LOGIN_USER]as LoginRequest)
    }

    override suspend fun getProducts(): List<ProductResponse> {
        return apiService.getProducts()
    }


}