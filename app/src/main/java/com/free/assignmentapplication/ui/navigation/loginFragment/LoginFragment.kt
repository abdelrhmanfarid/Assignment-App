package com.free.assignmentapplication.ui.navigation.loginFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.free.assignmentapplication.data.model.requestModels.loginRequestModel.LoginRequest
import com.free.assignmentapplication.data.model.requestModels.signupRequestModel.SignupRequest
import com.free.assignmentapplication.databinding.FragmentLoginBinding
import com.free.assignmentapplication.ui.navigation.signupFragment.SignUpFragmentDirections
import com.free.assignmentapplication.utils.LiveDataResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginButton.setOnClickListener {

            if (getUserLoginData() == null) {
                Toast.makeText(requireContext(), "please fill empty inputs", Toast.LENGTH_SHORT)
                    .show()

            } else {
                binding.progressBar.visibility = View.VISIBLE
                loginViewModel.loginUser(getUserLoginData()!!)
            }

        }
        observeUserLogin()
    }


    private fun observeUserLogin() {
        lifecycleScope.launchWhenStarted {
            loginViewModel.loginLiveData.collectLatest { event ->
                event.getContentIfNotHandled()?.let { resource ->
                    when (resource) {
                        is LiveDataResource.Success -> {

                            Log.d("teeeeeeeeeeeeet",resource.data!!.accessToken)
                            val direction =
                                LoginFragmentDirections.actionLoginFragmentToHomeFragment()
                            findNavController().navigate(direction)
                        }

                        is LiveDataResource.ErrorResponse -> {
                            //Log.d("ErrorResponse", resource.message.toString())
                            binding.progressBar.visibility = View.GONE
                            binding.loginButton.isEnabled = true
                        }

                        is LiveDataResource.Exception -> {
                            Log.d("Exception", "Exception")
                            loginViewModel.showAlertDialog(
                                "password must contain only letters and numbers",
                                requireContext()
                            )
                            binding.progressBar.visibility = View.GONE
                            binding.loginButton.isEnabled = true
                        }

                        is LiveDataResource.Loading -> {
                            Log.d("Loading", "Loading")
                            binding.progressBar.visibility = View.VISIBLE
                            binding.loginButton.isEnabled = false
                        }

                        is LiveDataResource.NoNetwork -> {
                            Log.d("NoNetwork", "NoNetwork")
                        }

                        else -> {
                            // Handle other cases if necessary
                        }
                    }
                }
            }
        }
    }


    private fun getUserLoginData(): LoginRequest? {

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        return if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            null
        } else {
            LoginRequest(email, password)
        }

    }

    override fun onPause() {
        super.onPause()
        binding.emailEditText.text.clear()
        binding.passwordEditText.text.clear()
    }
}