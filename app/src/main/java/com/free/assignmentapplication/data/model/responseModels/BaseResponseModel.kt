package com.free.assignmentapplication.data.model.responseModels

open class BaseResponseModel {
    val message: List<String>? = null
    var error: String? = null
    var statusCode: Int? = null
}