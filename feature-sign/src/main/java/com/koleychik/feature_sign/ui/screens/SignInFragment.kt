package com.koleychik.feature_sign.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kaleichyk.utils.navigation.NavigationSystem
import com.koleychik.basic_resource.isEnabledViews
import com.koleychik.basic_resource.showToast
import com.koleychik.core_authentication.api.AuthRepository
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_sign.databinding.FragmentSignInBinding
import com.koleychik.feature_sign.di.SignFeatureComponentHolder
import com.koleychik.feature_sign.navigation.SignInNavigationApi
import com.koleychik.feature_sign.ui.viewModels.SignInViewModel
import com.koleychik.feature_sign.ui.viewModels.SignViewModelFactory
import com.koleychik.models.states.CheckDataState
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
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        SignFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        createOnClickListener()
        subscribe()
    }

    private fun subscribe() {
        viewModel.loginState.observe(viewLifecycleOwner) {
            loading(false)
            when (it) {
                is CheckDataState.Checking -> loading(true)
                is CheckDataState.Error -> error(it.message)
                is CheckDataState.Successful -> navigationApi.fromSignInFragmentToMainScreen()
            }
        }
    }

    private fun loading(start: Boolean = true) {
        loadingApi.isVisible = start
        binding.root.isEnabledViews(!start)
    }

    private fun error(message: String) {
        requireContext().showToast(message)
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
                        viewModel.googleSignInUserResult(it)
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
            textSignUp.setOnClickListener(onClickListener)
        }
    }

    private fun setupLoading() {
        loadingApi.create(requireView())
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