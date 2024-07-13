package com.free.assignmentapplication.data.model.responseModels.signupResponseModel

import com.free.assignmentapplication.data.model.responseModels.BaseResponseModel

data class SignupResponse(
    val email: String,
    val password: String,
    val name: String,
    val avatar: String,
    val role: String,
    val id: Long,
    val creationAt: String,
    val updatedAt: String
) : BaseResponseModel()
