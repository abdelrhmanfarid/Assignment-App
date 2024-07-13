package com.free.assignmentapplication.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.free.assignmentapplication.data.model.responseModels.BaseResponseModel

abstract class BaseViewModel : ViewModel() {

    var params = mutableMapOf<String, Any>()
    var errorDialog: MutableLiveData<String> = MutableLiveData()
    var successDialog: MutableLiveData<String> = MutableLiveData()

    abstract fun stop()
    abstract fun start()


    fun showErrorMessage(baseModel: BaseResponseModel) {
        errorDialog.value = baseModel.message
    }

    fun showSuccessMessage(baseModel: BaseResponseModel) {

        successDialog.value = baseModel.message

    }
}