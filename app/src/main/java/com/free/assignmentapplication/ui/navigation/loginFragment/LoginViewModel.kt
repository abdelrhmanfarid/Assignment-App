package com.free.assignmentapplication.ui.navigation.loginFragment

import android.util.Log
import com.free.assignmentapplication.base.BaseViewModel
import com.free.assignmentapplication.data.local.LocalRepositoryImplementation
import com.free.assignmentapplication.data.model.requestModels.loginRequestModel.LoginRequest
import com.free.assignmentapplication.data.model.responseModels.loginResponseModel.LoginResponse
import com.free.assignmentapplication.data.model.responseModels.signupResponseModel.SignupResponse
import com.free.assignmentapplication.data.remote.MyRequestMap
import com.free.assignmentapplication.data.useCases.loginUseCase.LoginUseCase
import com.free.assignmentapplication.utils.Event
import com.free.assignmentapplication.utils.LiveDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val mainRepository: LocalRepositoryImplementation,
) : BaseViewModel() {

    private val _loginLiveData: MutableStateFlow<Event<LiveDataResource<LoginResponse>>> =
        MutableStateFlow(Event(LiveDataResource.EmptyState()))
    val loginLiveData: StateFlow<Event<LiveDataResource<LoginResponse>>> get() = _loginLiveData





    fun loginUser(loginRequest: LoginRequest) {

        _loginLiveData.value = Event(LiveDataResource.Loading())
        params[MyRequestMap.LOGIN_USER] = loginRequest

        loginUseCase.execute({
            onComplete {
                if (it.statusCode == 400) {

                    _loginLiveData.value = Event(LiveDataResource.ErrorResponse(it))
                } else {
                    mainRepository.saveString("token",it.accessToken)
                    _loginLiveData.value =Event (LiveDataResource.Success(it))
                }
            }
            onCancel {
                Log.d("login task", "canceled")
            }
            onError {
                _loginLiveData.value = Event(LiveDataResource.Exception())
            }
        },params)

    }

    override fun stop() {
        loginUseCase.unsubscribe()
    }

    override fun start() {
    }

}