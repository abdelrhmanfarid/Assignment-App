package com.free.assignmentapplication.data.useCases.signupUseCase

import com.free.assignmentapplication.data.BaseUseCase
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse
import com.free.assignmentapplication.data.remote.DataRepository
import javax.inject.Inject

class SignupUseCase @Inject constructor(dataRepository: DataRepository) :
    BaseUseCase<SignupResponse>(dataRepository) {
    override suspend fun executeOnBackground(
        map: MutableMap<String, Any>,
        headerMap: Map<String, Any>?
    ): SignupResponse {
        return dataRepository.signupUser(map)
    }


}