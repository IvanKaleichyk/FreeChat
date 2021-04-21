package com.koleychik.feature_password_utils.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.koleychik.basic_resource.isEnabledViews
import com.koleychik.basic_resource.showToast
import com.koleychik.dialogs.InfoDialog
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.feature_password_utils.R
import com.koleychik.feature_password_utils.SpecifyEmailNavigationApi
import com.koleychik.feature_password_utils.databinding.FragmentSpecifyEmailBinding
import com.koleychik.feature_password_utils.di.PasswordUtilsFeatureComponentHolder
import com.koleychik.feature_password_utils.viewModels.SpecifyEmailViewModel
import com.koleychik.feature_password_utils.viewModels.ViewModelFactory
import com.koleychik.models.results.CheckResult
import com.koleychik.module_injector.NavigationSystem
import javax.inject.Inject

class SpecifyEmailFragment : Fragment() {

    private var _binding: FragmentSpecifyEmailBinding? = null
    private val binding get() = _binding!!

    private val dialog by lazy {
        InfoDialog(
            requireContext(),
            R.string.reset_password,
            R.string.text_about_email_message
        ) {
            it.dismiss()
            navigationApi.fromSpecifyEmailToSignIn()
        }.apply {
            setBtnTextRes(R.string.ok)
        }
    }

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[SpecifyEmailViewModel::class.java]
    }

    @Inject
    internal lateinit var navigationApi: SpecifyEmailNavigationApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentSpecifyEmailBinding.inflate(inflater, container, false)
        PasswordUtilsFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLoading()
        createOnClickListener()
        subscribe()
    }

    private fun subscribe() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            loadingApi.isVisible = isLoading
            binding.rootLayout.isEnabledViews(!isLoading)
        }
        viewModel.checkRes.observe(viewLifecycleOwner) {
            when (it) {
                null -> {
                }
                is CheckResult.Successful -> dialog.show()
                is CheckResult.DataError -> requireContext().showToast(it.message)
                is CheckResult.ServerError -> requireContext().showToast(it.message)
            }
        }
    }

    private fun createOnClickListener() {
        binding.btn.setOnClickListener {
            viewModel.resetPassword(binding.edtName.text.toString().trim())
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