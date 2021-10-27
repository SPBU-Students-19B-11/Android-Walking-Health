package com.ewake.walkinghealth.presentation.ui.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.ewake.walkinghealth.databinding.FragmentLoginBinding
import com.ewake.walkinghealth.presentation.viewmodel.login.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

/**
 * @author Nikolaevsky Dmitry (@d.nikolaevskiy)
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding
        get() = _binding!!

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.apply {
            enter.setOnClickListener {
                if (validateData()) {
                    viewModel.onLoginButtonClicked(
                        binding.login.text.toString(),
                        binding.password.text.toString()
                    )
                }
            }

            forgetPassword.setOnClickListener {
                viewModel.onForgetPasswordClicked()
            }

            register.setOnClickListener {
                viewModel.onRegisterButtonClicked()
            }
        }

        viewModel.apply {
            messageLiveData.observe(viewLifecycleOwner, ::showMessage)
            navigationLiveData.observe(viewLifecycleOwner, ::navigate)
        }

        return binding.root
    }

    private fun validateData(): Boolean {
        var result = true

        binding.apply {
            loginContainer.error = if (login.text.isNullOrEmpty()) {
                result = false
                "Введите логин"
            } else {
                null
            }

            passwordContainer.error = if (password.text.isNullOrEmpty()) {
                result = false
                "Введите пароль"
            } else {
                null
            }
        }

        return result
    }

    private fun showMessage(string: String) {
        Snackbar.make(binding.root, string, Snackbar.LENGTH_SHORT).show()
    }

    private fun navigate(action: NavDirections) {
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}