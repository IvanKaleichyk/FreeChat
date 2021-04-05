package com.koleychik.feature_start.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.koleychik.basic_resource.showToast
import com.koleychik.core_authentication.result.CheckResult
import com.koleychik.feature_start.R
import com.koleychik.feature_start.StartFeatureNavigation
import com.koleychik.feature_start.StartRepository
import com.koleychik.feature_start.di.StartFeatureComponentHolder
import javax.inject.Inject

class VerifyEmailFragment : Fragment() {

    @Inject
    internal lateinit var repository: StartRepository

    @Inject
    internal lateinit var navigation: StartFeatureNavigation

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_verify_email, container, false)
        StartFeatureComponentHolder.getComponent().inject(this)
        root.findViewById<Button>(R.id.btn).setOnClickListener {
            check()
        }
        return root
    }

    private fun check() {
        repository.checkVerifiedEmail {
            when (it) {
                is CheckResult.Successful -> navigation.fromVerifyEmailFragmentToMainScreen()
                is CheckResult.DataError -> requireContext().showToast(it.message)
                is CheckResult.ServerError -> requireContext().showToast(it.message)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        check()
    }

}