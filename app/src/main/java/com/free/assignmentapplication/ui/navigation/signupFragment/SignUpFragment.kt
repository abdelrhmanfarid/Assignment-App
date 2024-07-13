package com.free.assignmentapplication.ui.navigation.signupFragment

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
import com.free.assignmentapplication.data.model.requestModels.signupRequestModel.SignupRequest
import com.free.assignmentapplication.databinding.FragmentSignUpBinding
import com.free.assignmentapplication.ui.navigation.loginFragment.LoginFragmentDirections
import com.free.assignmentapplication.utils.LiveDataResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val signUpViewModel: SignUpViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.signupButton.setOnClickListener {

            if (getUserRegisterData()==null){
                Toast.makeText(requireContext(),"please fill empty inputs",Toast.LENGTH_SHORT).show()
            }else{
                binding.progressBar.visibility = View.VISIBLE
                signUpViewModel.registerUser(getUserRegisterData()!!)
            }

        }
        observeUserSignup()
    }

    private fun observeUserSignup() {
        lifecycleScope.launchWhenStarted {
            signUpViewModel.signupLiveData.collectLatest { event ->
                event.getContentIfNotHandled()?.let { resource ->
                    when (resource) {
                        is LiveDataResource.Success -> {
                            val direction =
                                SignUpFragmentDirections.actionSignUpFragmentToLoginFragment()
                            findNavController().navigate(direction)
                        }

                        is LiveDataResource.ErrorResponse -> {
                            Log.d("ErrorResponse", resource.message.toString())
                        }

                        is LiveDataResource.Exception -> {
                            Log.d("Exception", "Exception")
                        }

                        is LiveDataResource.Loading -> {
                            Log.d("Loading", "Loading")
                            binding.progressBar.visibility = View.VISIBLE
                            binding.signupButton.isEnabled = false
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

    private fun getUserRegisterData(): SignupRequest? {
        val name = binding.nameEditText.text.toString()
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        return if (name.isNullOrEmpty()||email.isNullOrEmpty()||password.isNullOrEmpty()){
            null
        }else{
            SignupRequest(name, email, password, "https://picsum.photos/800")
        }


    }


}