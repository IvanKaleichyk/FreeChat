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
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_sign.databinding.FragmentSignUpBinding
import com.koleychik.feature_sign.di.SignFeatureComponentHolder
import com.koleychik.feature_sign.navigation.SignUpNavigationApi
import com.koleychik.feature_sign.ui.viewModels.SignUpViewModel
import com.koleychik.feature_sign.ui.viewModels.SignViewModelFactory
import com.koleychik.feature_sign.underlineText
import com.koleychik.models.states.CheckDataState
import javax.inject.Inject

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: SignViewModelFactory

    @Inject
    internal lateinit var navigationApi: SignUpNavigationApi

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[SignUpViewModel::class.java]
    }

    @Inject
    internal lateinit var authRepository: AuthRepository

    @Inject
    internal lateinit var loadingApi: LoadingApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        com.kaleichyk.utils.navigation.NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        SignFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textSignIn.underlineText()
        createOnCLickListener()
        setupLoading()
        subscribe()
    }

    private fun subscribe() {
        viewModel.createAccountState.observe(viewLifecycleOwner) {
            loading(false)
            when (it) {
                is CheckDataState.Checking -> loading(true)
                is CheckDataState.Error -> error(it.message)
                is CheckDataState.Successful -> navigationApi.fromSignUpToMainScreen()
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


    private fun createOnCLickListener() {
        val onClickListener = View.OnClickListener {
            with(binding) {
                when (it.id) {
                    textSignIn.id -> navigationApi.fromSignUpToSignIn()
                    btn.id -> checkInformationAndCreateUser()
                    googleAuth.id -> authRepository.googleSingIn(requireActivity() as AppCompatActivity) {
                        viewModel.googleSignInUserResult(it)
                    }
                }
            }
        }

        with(binding) {
            btn.setOnClickListener(onClickListener)
            googleAuth.setOnClickListener(onClickListener)
            textSignIn.setOnClickListener(onClickListener)
        }
    }

    private fun checkInformationAndCreateUser() {
        var name: String
        val email: String
        val password: String
        val repeatPassword: String
        with(binding) {
            name = edtName.text.toString().trim()
            email = edtEmail.text.toString().trim()
            password = edtPassword.text.toString().trim()
            repeatPassword = edtRepeatPassword.text.toString().trim()
        }
        viewModel.startCreateAccount(name, email, password, repeatPassword)
    }


    private fun setupLoading() {
        loadingApi.create(requireView())
        with(binding.viewStubLoading) {
            layoutResource = loadingApi.layoutRes
            inflate()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        SignFeatureComponentHolder.reset()
    }
}