package com.free.assignmentapplication.data.useCases.loginUseCase

import com.free.assignmentapplication.data.BaseUseCase
import com.free.assignmentapplication.data.model.responseModels.loginResponseModel.LoginResponse
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse
import com.free.assignmentapplication.data.remote.DataRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(dataRepository: DataRepository) :
    BaseUseCase<LoginResponse>(dataRepository) {
    override suspend fun executeOnBackground(
        map: MutableMap<String, Any>,
        headerMap: Map<String, Any>?
    ): LoginResponse {
        return dataRepository.loginUser(map)
    }
}