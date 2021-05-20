package com.kaleichyk.feature_user_info.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import coil.load
import com.kaleichyk.feature_user_info.*
import com.kaleichyk.feature_user_info.databinding.FragmentUserInfoBinding
import com.kaleichyk.feature_user_info.di.UserInfoFeatureComponentHolder
import com.kaleichyk.feature_user_info.ui.viewModel.UserInfoViewModel
import com.kaleichyk.feature_user_info.ui.viewModel.ViewModelFactory
import com.kaleichyk.utils.CurrentUser
import com.kaleichyk.utils.MimeTypes.MIME_TYPE_IMAGE
import com.kaleichyk.utils.NavigationConstants.DIALOG_ID
import com.kaleichyk.utils.NavigationConstants.USER
import com.koleychik.basic_resource.isEnabledViews
import com.koleychik.basic_resource.showToast
import com.koleychik.core_authentication.checkEmail
import com.koleychik.core_authentication.checkName
import com.koleychik.core_authentication.checkPassword
import com.koleychik.dialogs.DialogGetData
import com.koleychik.dialogs.DialogGetDataListener
import com.koleychik.dialogs.DialogInfo
import com.koleychik.dialogs.DialogInfoListener
import com.koleychik.feature_loading_api.LoadingApi
import com.koleychik.models.asRoot
import com.koleychik.models.results.CheckResult
import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState
import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot
import com.koleychik.module_injector.NavigationSystem
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserInfoFragment : Fragment() {

    companion object {
        const val SET_DATA_FUNCTION = 3
        const val DELETE_USER_FUNCTION = 1
        const val CREATE_NEW_DIALOG = 3
    }

    private var loadingStarter: Int? = null

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!

    private val user: User by lazy {
        val us = requireArguments().getParcelable<User>(USER)!!
        if (us.id == CurrentUser.user?.id) us.asRoot()
        else us
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var navigationApi: UserInfoNavigationApi

    private val dialogGetNameListener by lazy {
        object : DialogGetDataListener {
            override fun onPositiveClick(dialog: DialogInterface, value: String) {
                lifecycleScope.launch {
                    val checkRes = checkName(value)
                    handleCheckResult(checkRes) {
                        dialog.dismiss()
                        viewModel.setName(value)
                    }
                }
            }
        }
    }

    private val dialogGetEmailListener by lazy {
        object : DialogGetDataListener {
            override fun onPositiveClick(dialog: DialogInterface, value: String) {
                lifecycleScope.launch {
                    val checkRes = checkEmail(value)
                    handleCheckResult(checkRes) {
                        dialog.dismiss()
                        viewModel.setEmail(value)
                    }
                }
            }
        }
    }

    private val dialogGetPasswordListener by lazy {
        object : DialogGetDataListener {
            override fun onPositiveClick(dialog: DialogInterface, value: String) {
                lifecycleScope.launch {
                    val checkRes = checkPassword(value, value)
                    handleCheckResult(checkRes) {
                        dialog.dismiss()
                        viewModel.setPassword(value)
                    }
                }
            }
        }
    }

    private val dialogInfoListener by lazy {
        object : DialogInfoListener {
            override fun onClick(dialog: DialogInterface) {
                dialog.cancel()
            }
        }
    }

    private val dialogDeleteUserListener by lazy {
        object : DialogInfoListener {
            override fun onClick(dialog: DialogInterface) {
                dialog.dismiss()
                viewModel.deleteUser(user.id)
            }
        }
    }

    private val dialogSetName by lazy {
        DialogGetData(
            R.string.set_name,
            null,
            R.string.name,
            dialogGetNameListener,
            R.string.set,
            R.string.cancel,
        )
    }

    private val dialogSetEmail by lazy {
        DialogGetData(
            R.string.set_email,
            null,
            R.string.email,
            dialogGetEmailListener,
            R.string.set,
            R.string.cancel,
        )
    }

    private val dialogSetPassword by lazy {
        DialogGetData(
            R.string.set_password,
            null,
            R.string.password,
            dialogGetPasswordListener,
            R.string.set,
            R.string.cancel,
        )
    }

    private val dialogDelete by lazy {
        DialogInfo(
            dialogDeleteUserListener,
            requireContext().getString(R.string.want_to_delete_account),
            null,
            R.string.yes
        )
    }

    private val pickUserIcon: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it == null) return@registerForActivityResult
            viewModel.setIcon(user.id, it, MIME_TYPE_IMAGE)
        }

    private val pickUserBackground: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it == null) return@registerForActivityResult
            viewModel.setBackground(user.id, it, MIME_TYPE_IMAGE)
        }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[UserInfoViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentUserInfoBinding.inflate(inflater, container, false)
        UserInfoFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (user is UserRoot) setRootUserInfo(user as UserRoot)
        else setUserInfo(user)
        subscribe()
        createOnCLickListener()
        setupLoading()
    }

    private fun subscribe() {
        viewModel.createNewDialogState.observe(viewLifecycleOwner) {
            stopLoading(CREATE_NEW_DIALOG)
            when (it) {
                is DataState.Loading -> startLoading(CREATE_NEW_DIALOG)
                is DataState.Result<*> -> goToMessageFeature(it.body as Long)
                is DataState.Error -> error(it.message)
                else -> {
                }
            }
        }
        viewModel.deleteUserRequest.observe(viewLifecycleOwner) {
            stopLoading(DELETE_USER_FUNCTION)
            when (it) {
                is CheckDataState.Checking -> startLoading(DELETE_USER_FUNCTION)
                is CheckDataState.Error -> error(it.message)
                is CheckDataState.Successful -> navigationApi.fromUserInfoFeatureToSignFeature()
            }
        }
        viewModel.setDataState.observe(viewLifecycleOwner) {
            stopLoadingUserInfo()
            when (it) {
                is CheckDataState.Checking -> startLoadingUserInfo()
                is CheckDataState.Error -> error(it.message)
                is CheckDataState.Successful -> setRootUserInfo(CurrentUser.user!!)
            }
        }
    }

    private fun startLoading(starter: Int) {
        loadingStarter = starter
        loadingApi.isVisible = true
        binding.root.isEnabledViews(false)
    }

    private fun stopLoading(starter: Int?) {
        if (loadingStarter != starter && starter != null) return
        loadingApi.isVisible = false
        binding.root.isEnabledViews(true)
    }

    private fun goToMessageFeature(dialogId: Long) {
        navigationApi.fromUserInfoFeatureToMessagesFeature(
            Bundle().apply {
                putLong(DIALOG_ID, dialogId)
            })
    }

    private inline fun handleCheckResult(res: CheckResult, onSuccessful: () -> Unit) {
        when (res) {
            is CheckResult.Successful -> onSuccessful()
            is CheckResult.Error -> requireContext().showToast(res.message)
        }
    }

    private fun setUserInfo(user: User) {
        with(binding) {
            groupRootUser.visibility = View.GONE
            groupUserInfo.visibility = View.VISIBLE
            email.text = user.email
            name.text = user.name
            user.icon?.let { icon.load(it) }
            user.background?.let { background.load(it) }
        }
    }

    private fun setRootUserInfo(user: UserRoot) {
        with(binding) {
            groupRootUser.visibility = View.VISIBLE
            groupUserInfo.visibility = View.GONE
            email.text = user.email
            name.text = user.name
            user.icon?.let { icon.load(it) }
            user.background?.let { background.load(it) }
        }
    }

    private fun error(message: String) {
        DialogInfo(
            dialogInfoListener,
            requireContext().getString(R.string.error),
            message,
        ).show(childFragmentManager, "Dialog UserInfo error")
    }

    private fun createOnCLickListener() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                R.id.textStartDialog -> startDialog()
                R.id.textSetName -> startSetName()
                R.id.textSetEmail -> startSetEmail()
                R.id.textSetPassword -> startSetPassword()
                R.id.textDeleteAccount -> deleteAccount()
                R.id.textSignOut -> signOut()
            }
        }

        with(binding) {
            textStartDialog.setOnClickListener(onClickListener)
            textSetName.setOnClickListener(onClickListener)
            textSetEmail.setOnClickListener(onClickListener)
            textSetPassword.setOnClickListener(onClickListener)
            textDeleteAccount.setOnClickListener(onClickListener)
            textSignOut.setOnClickListener(onClickListener)
        }

        val onCLickListenerForChooseImage = View.OnClickListener {
            if (user !is UserRoot) return@OnClickListener
            when (it.id) {
                R.id.icon -> pickUserIcon.launch(MIME_TYPE_IMAGE)
                R.id.framework -> return@OnClickListener
                R.id.background -> pickUserBackground.launch(MIME_TYPE_IMAGE)
            }
        }

        with(binding) {
            icon.setOnClickListener(onCLickListenerForChooseImage)
            background.setOnClickListener(onCLickListenerForChooseImage)
            framework.setOnClickListener(onCLickListenerForChooseImage)
        }

    }

    private fun startDialog() {
        val listUsers = listOf(CurrentUser.user!!, user)
        viewModel.createNewDialog(createDialog(listUsers))
    }

    private fun deleteAccount() {
        dialogDelete.show(
            childFragmentManager,
            "Dialog to Delete account"
        )
    }

    private fun signOut() {
        viewModel.signOut()
        navigationApi.fromUserInfoFeatureToSignFeature()
    }

    private fun startSetName() {
        dialogSetName.show(childFragmentManager, "Set Name Dialog")
    }

    private fun startSetEmail() {
        dialogSetEmail.show(childFragmentManager, "Set Email Dialog")
    }

    private fun startSetPassword() {
        dialogSetPassword.show(childFragmentManager, "Set Email Dialog")
    }

    private fun startLoadingUserInfo() {
        binding.shimmerUserInfo.run {
            startShimmer()
            visibility = View.VISIBLE
        }
    }

    private fun stopLoadingUserInfo() {
        binding.shimmerUserInfo.run {
            stopShimmer()
            visibility = View.GONE
        }
    }

    private fun setupLoading() {
        loadingApi.create(requireView())
        binding.viewStubLoading.run {
            layoutResource = loadingApi.layoutRes
            inflate()
        }
        stopLoading(null)
    }

    override fun onStop() {
        super.onStop()
        UserInfoFeatureComponentHolder.reset()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}