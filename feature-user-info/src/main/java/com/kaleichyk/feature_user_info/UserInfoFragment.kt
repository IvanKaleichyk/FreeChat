package com.kaleichyk.feature_user_info

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.ImageLoader
import coil.load
import coil.request.ImageRequest
import com.kaleichyk.data.CurrentUser
import com.kaleichyk.data.NavigationConstants.USER
import com.kaleichyk.feature_select_image_api.SelectImageApi
import com.kaleichyk.feature_user_info.databinding.FragmentUserInfoBinding
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.models.DeviceImage
import com.koleychik.models.results.CheckResult
import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot
import javax.inject.Inject


class UserInfoFragment : Fragment() {

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!

    private val user: User by lazy {
        val us = requireArguments().getParcelable<User>(USER)!!
        if (us.id == CurrentUser.user?.id) us as UserRoot
        else us
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var selectImageApi: SelectImageApi

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (user is UserRoot) setRootUserInfo(user as UserRoot)
        else setUserInfo(user)
        subscribe()
        createOnCLickListener()
        setupLoading()
        setupDeviceImagesRv()
    }

    private fun subscribe() {
        if (user is UserRoot) {
            viewModel.listImages.observe(viewLifecycleOwner) {
                when (it) {
                    null -> loadImages()
                    else -> setImagesList(it)
                }
            }
        }
        viewModel.serverRequest.observe(viewLifecycleOwner) {
            when (it) {
                is CheckResult.Successful -> stopLoading()
                is CheckResult.DataError -> error(requireContext().getString(it.message))
                is CheckResult.ServerError -> error(it.message)
            }
        }
    }


    private fun setImagesList(list: List<DeviceImage>) {

    }

    private fun loadImages() {

    }

    private fun stopLoading() {
        loadingApi.isVisible = false
    }

    private fun setUserInfo(user: User) {
        with(binding) {
            groupRootUser.visibility = View.GONE
            groupUserInfo.visibility = View.VISIBLE
            email.text = user.email
            name.text = user.name
            user.icon?.let { icon.load(it) }
            user.background?.let { loadBackground(background, it) }
        }
    }

    private fun setRootUserInfo(user: UserRoot) {
        with(binding) {
            groupRootUser.visibility = View.VISIBLE
            groupUserInfo.visibility = View.GONE
            email.text = user.email
            name.text = user.name
            user.icon?.let { icon.load(it) }
            user.background?.let { loadBackground(background, it) }
        }
    }

    private fun error(message: String) {

    }

    private fun createOnCLickListener() {

    }

    private fun setupLoading() {
        loadingApi.create(requireView())
        binding.viewStubLoading.run {
            layoutResource = loadingApi.layoutRes
            inflate()
        }
    }

    private fun setupDeviceImagesRv() {

    }

    private fun loadBackground(view: View, uri: Uri) {
        val imageLoader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data(uri)
            .target {
                view.background = it
            }
            .build()
        imageLoader.enqueue(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}