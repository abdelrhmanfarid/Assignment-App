package com.free.assignmentapplication.ui.navigation.signupFragment

import android.util.Log
import com.free.assignmentapplication.base.BaseViewModel
import com.free.assignmentapplication.data.local.LocalRepositoryImplementation
import com.free.assignmentapplication.data.model.requestModels.signupRequestModel.SignupRequest
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse
import com.free.assignmentapplication.data.remote.MyRequestMap
import com.free.assignmentapplication.data.useCases.signupUseCase.SignupUseCase
import com.free.assignmentapplication.utils.Event
import com.free.assignmentapplication.utils.LiveDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val mainRepository: LocalRepositoryImplementation
) : BaseViewModel() {


    private val _signupLiveData: MutableStateFlow<Event<LiveDataResource<SignupResponse>>> =
        MutableStateFlow(Event(LiveDataResource.EmptyState()))
    val signupLiveData: StateFlow<Event<LiveDataResource<SignupResponse>>> get() = _signupLiveData


    fun registerUser(signupRequest: SignupRequest) {

        _signupLiveData.value = Event(LiveDataResource.Loading())
        params[MyRequestMap.SIGNUP_USER] = signupRequest

        signupUseCase.execute({
            onComplete {

                if (it.statusCode == 400) {

                    _signupLiveData.value = Event(LiveDataResource.ErrorResponse(it))
                } else {


                    _signupLiveData.value = Event(LiveDataResource.Success(it))
                }
            }
            onCancel {
                Log.d("signup task", "canceled")
            }
            onError {
                _signupLiveData.value = Event(LiveDataResource.Exception())
            }
        }, params)

    }

    override fun stop() {
        signupUseCase.unsubscribe()
    }

    override fun start() {

    }

    fun getToken(): String {

        return mainRepository.returnString("token")

    }


}