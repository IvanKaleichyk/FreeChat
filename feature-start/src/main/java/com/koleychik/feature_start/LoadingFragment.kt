package com.koleychik.feature_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.feature_start.databinding.FragmentLoadingBinding
import com.koleychik.feature_start.di.LoadingFeatureComponentHolder
import javax.inject.Inject

class LoadingFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding = _binding!!

    @Inject
    internal lateinit var authRepository: AuthRepository

    @Inject
    internal lateinit var navigation: LoadingNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        LoadingFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepository.checkUser {
            when (it) {
                is UserResult.Successful -> navigation.goToMainScreen(null)
                else -> navigation.goToAuthorization(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        LoadingFeatureComponentHolder.reset()
    }

}