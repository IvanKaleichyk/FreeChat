package com.koleychik.feature_start

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.koleychik.core_authorization.newapi.AuthRepository
import com.koleychik.core_authorization.result.user.UserResult
import com.koleychik.feature_start.databinding.FragmentLoadingBinding
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import javax.inject.Inject

class StartFragment : Fragment() {

    private var _binding: FragmentLoadingBinding? = null
    private val binding = _binding!!

    @Inject
    internal lateinit var authRepository: AuthRepository

    @Inject
    internal lateinit var navigation: StartFeatureNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadingBinding.inflate(inflater, container, false)
        StartFeatureComponentHolder.getComponent().inject(this)

        TODO("SHECK PERMISSIONS")

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepository.checkUser {
            when (it) {
                is UserResult.Successful -> navigation.fromStartFragmentGoToMainScreen(null)
                else -> navigation.fromStartFragmentGoToAuthorization(null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        StartFeatureComponentHolder.reset()
    }

}