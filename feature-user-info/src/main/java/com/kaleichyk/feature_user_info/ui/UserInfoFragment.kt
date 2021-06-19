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
import com.kaleichyk.utils.navigation.NavigationConstants.DIALOG
import com.kaleichyk.utils.navigation.NavigationConstants.USER_ID
import com.kaleichyk.utils.navigation.NavigationSystem
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
import com.koleychik.models.dialog.Dialog
import com.koleychik.models.results.CheckResult
import com.koleychik.models.states.CheckDataState
import com.koleychik.models.states.DataState
import com.koleychik.models.users.User
import com.koleychik.models.users.UserRoot
import kotlinx.coroutines.launch
import javax.inject.Inject


class UserInfoFragment : Fragment() {

    companion object {
        const val USER_STATE = 0
        const val SET_DATA_FUNCTION = 1
        const val DELETE_USER_FUNCTION = 2
        const val CREATE_NEW_DIALOG = 3
    }

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var loadingApi: LoadingApi

    @Inject
    internal lateinit var navigationApi: UserInfoNavigationApi

    private var loadingStarter: Int? = null

    private var shimmerLoadingStarter: Int? = null

    private var _binding: FragmentUserInfoBinding? = null
    private val binding get() = _binding!!

    private val userId: String by lazy {
        requireArguments().getString(USER_ID)!!
    }

    private lateinit var user: User

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
                viewModel.deleteUser(userId)
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
            viewModel.setIcon(userId, it, MIME_TYPE_IMAGE)
        }

    private val pickUserBackground: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            if (it == null) return@registerForActivityResult
            viewModel.setBackground(userId, it, MIME_TYPE_IMAGE)
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

        subscribe()
        createOnCLickListener()
        setupLoading()
    }

    private fun subscribe() {
        viewModel.userState.observe(viewLifecycleOwner) {
            stopShimmerLoading(USER_STATE)
            when (it) {
                is DataState.WaitingForStart -> viewModel.getUserById(userId)
                is DataState.Loading -> startShimmerLoading(USER_STATE)
                is DataState.Error -> error(it.message)
                is DataState.Result<*> -> {
                    val user = it.body as User
                    if (user.id == CurrentUser.user?.id) setRootUserInfo(user.asRoot())
                    else setUserInfo(user)
                }
            }
        }

        viewModel.createNewDialogState.observe(viewLifecycleOwner) {
            stopLoading(CREATE_NEW_DIALOG)
            when (it) {
                is DataState.Loading -> startLoading(CREATE_NEW_DIALOG)
                is DataState.Result<*> -> goToMessageFeature((it.body as Dialog))
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
            stopShimmerLoading(SET_DATA_FUNCTION)
            when (it) {
                is CheckDataState.Checking -> startShimmerLoading(SET_DATA_FUNCTION)
                is CheckDataState.Error -> error(it.message)
                is CheckDataState.Successful -> setRootUserInfo(CurrentUser.user!!)
            }
        }
    }

    private fun startShimmerLoading(starter: Int) {
        shimmerLoadingStarter = starter
        with(binding) {
            groupRootUser.visibility = View.GONE
            groupUserInfo.visibility = View.GONE

            shimmerUserInfo.run {
                startShimmer()
                visibility = View.VISIBLE
            }
        }
    }

    private fun stopShimmerLoading(starter: Int) {
        if (shimmerLoadingStarter != starter) return

        if (::user.isInitialized) {
            if (user is UserRoot) binding.groupRootUser.visibility = View.VISIBLE
            else binding.groupUserInfo.visibility = View.VISIBLE
        }

        binding.shimmerUserInfo.run {
            stopShimmer()
            visibility = View.GONE
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

    private fun goToMessageFeature(dialog: Dialog) {
        navigationApi.fromUserInfoFeatureToMessagesFeature(
            Bundle().apply {
                putParcelable(DIALOG, dialog)
            })
    }

    private fun setUserInfo(user: User) {
        this.user = user
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
        this.user = user
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
                R.id.carcassStartDialog -> startDialog()
                R.id.carcassSetName -> startSetName()
                R.id.carcassSetEmail -> startSetEmail()
                R.id.carcassSetPassword -> startSetPassword()
                R.id.carcassDeleteAccount -> deleteAccount()
                R.id.carcassSignOut -> signOut()
            }
        }

        with(binding) {
            carcassStartDialog.setOnClickListener(onClickListener)
            carcassSetName.setOnClickListener(onClickListener)
            carcassSetEmail.setOnClickListener(onClickListener)
            carcassSetPassword.setOnClickListener(onClickListener)
            carcassDeleteAccount.setOnClickListener(onClickListener)
            carcassSignOut.setOnClickListener(onClickListener)
        }

        val onCLickListenerForChooseImage = View.OnClickListener {
            if (user !is UserRoot) return@OnClickListener
            when (it.id) {
                R.id.icon -> pickUserIcon.launch(MIME_TYPE_IMAGE)
                R.id.framework -> return@OnClickListener
//                R.id.background -> pickUserBackground.launch(MIME_TYPE_IMAGE)
            }
        }

        with(binding) {
            icon.setOnClickListener(onCLickListenerForChooseImage)
//            background.setOnClickListener(onCLickListenerForChooseImage)
            framework.setOnClickListener(onCLickListenerForChooseImage)
        }

    }

    private fun startDialog() {
        viewModel.createNewDialog(createDialog(user.toDialogMember()))
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

    private fun setupLoading() {
        loadingApi.create(requireView())
        binding.viewStubLoading.run {
            layoutResource = loadingApi.layoutRes
            inflate()
        }
        stopLoading(null)
    }

    private inline fun handleCheckResult(res: CheckResult, onSuccessful: () -> Unit) {
        when (res) {
            is CheckResult.Successful -> onSuccessful()
            is CheckResult.Error -> requireContext().showToast(res.message)
        }
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