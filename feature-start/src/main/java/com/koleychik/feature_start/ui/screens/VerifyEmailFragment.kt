package com.koleychik.feature_start.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.koleychik.feature_start.R
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.StartManager
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import com.koleychik.module_injector.NavigationSystem
import javax.inject.Inject

class VerifyEmailFragment : Fragment() {

    @Inject
    internal lateinit var manager: StartManager

    @Inject
    internal lateinit var navigation: StartFeatureNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        val root = inflater.inflate(R.layout.fragment_verify_email, container, false)
        StartFeatureComponentHolder.getComponent().inject(this)
        root.findViewById<Button>(R.id.btn).setOnClickListener {
            navigation.fromVerifyEmailFragmentToMainScreen()
//            check()
        }
        return root
    }

    private fun check() {
//        manager.checkVerifiedEmail {
//            when (it) {
//                is VerificationResult.Successful -> navigation.fromVerifyEmailFragmentToMainScreen()
//                is VerificationResult.DataError -> requireContext().showToast(it.message)
//                is VerificationResult.ServerError -> requireContext().showToast(it.message)
//                is VerificationResult.Waiting -> requireContext().showToast(R.string.waiting)
//            }
//        }
    }

    override fun onResume() {
        super.onResume()
        check()
    }

}