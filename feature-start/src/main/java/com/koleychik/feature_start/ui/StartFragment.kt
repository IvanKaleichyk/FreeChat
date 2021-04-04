package com.koleychik.feature_start.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.core_authentication.result.user.UserResult
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.databinding.FragmentLoadingBinding
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import com.koleychik.feature_start.ui.viewModel.StartViewModel
import com.koleychik.feature_start.ui.viewModel.StartViewModelFactory
import com.koleychik.module_injector.NavigationSystem
import javax.inject.Inject

class StartFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding get() = _binding!!

    @Inject
    internal lateinit var viewModelFactory: StartViewModelFactory

    @Inject
    internal lateinit var navigation: StartFeatureNavigation

    private val viewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[StartViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        StartFeatureComponentHolder.getComponent().inject(this)
        subscribe()
        return binding.root
    }

    private fun subscribe() {
        viewModel.verificationResult.observe(viewLifecycleOwner) {
            when (it) {
                is CheckResult.Successful -> navigation.fromStartFragmentGoToMainScreen()
                else -> navigation.fromStartFragmentGoToInfoFragment()
            }
        }
        viewModel.userResult.observe(viewLifecycleOwner, {
            when (it) {
                null -> viewModel.checkUser()
                is UserResult.Successful -> viewModel.verificationResult
                else -> navigation.fromStartFragmentGoToAuthorization()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StartFeatureComponentHolder.reset()
    }

}