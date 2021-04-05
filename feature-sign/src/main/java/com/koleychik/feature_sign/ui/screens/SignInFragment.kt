package com.koleychik.feature_sign.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.koleychik.basic_resource.isEnabledViews
import com.koleychik.basic_resource.showToast
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_sign.R
import com.koleychik.feature_sign.databinding.FragmentSignInBinding
import com.koleychik.feature_sign.navigation.SignInNavigationApi
import com.koleychik.feature_sign.ui.viewModels.SignInViewModel
import com.koleychik.feature_sign.ui.viewModels.SignViewModelFactory
import javax.inject.Inject

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var authRepository: AuthRepository

    @Inject
    internal lateinit var viewModelFactory: SignViewModelFactory

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var navigationApi: SignInNavigationApi

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[SignInViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        createOnClickListener()
        subscribe()
    }

    private fun subscribe() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            loadingApi.isVisible = it
            binding.root.isEnabledViews(!it)
        }
        viewModel.checkResult.observe(viewLifecycleOwner) {
            when (it) {
                null -> {
                }
                is CheckResult.DataError -> requireContext().showToast(it.message)
                is CheckResult.ServerError -> requireContext().showToast(it.message)
                else -> requireContext().showToast(R.string.error)
            }
        }
        viewModel.userResult.observe(viewLifecycleOwner) {
            when (it) {
                null -> {
                }
                is UserResult.Successful -> navigationApi.fromSignInFragmentToMainScreen()
                is UserResult.DataError -> requireContext().showToast(it.message)
                is UserResult.ServerError -> requireContext().showToast(it.message)
                else -> requireContext().showToast(R.string.cannot_create_user)
            }
        }
    }

    private fun checkInformationAndLoginUser() {
        val email: String
        val password: String
        with(binding) {
            email = edtEmail.text.toString().trim()
            password = edtPassword.text.toString().trim()
        }
        viewModel.login(email, password, password)
    }

    private fun createOnClickListener() {
        val onClickListener = View.OnClickListener {
            with(binding) {
                when (it.id) {
                    btn.id -> checkInformationAndLoginUser()
                    googleAuth.id -> authRepository.googleSingIn(requireActivity() as AppCompatActivity) {
                        viewModel.userResult.value = it
                    }
                    textForgotPassword.id -> navigationApi.fromSignInFragmentToPasswordRecovery()
                    textSignUp.id -> navigationApi.fromSignInFragmentToSignUp()
                }
            }
        }
        with(binding) {
            btn.setOnClickListener(onClickListener)
            googleAuth.setOnClickListener(onClickListener)
            textForgotPassword.setOnClickListener(onClickListener)
        }
    }

    private fun setupLoading() {
        binding.viewStubLoading.run {
            layoutResource = loadingApi.layoutRes
            inflate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}