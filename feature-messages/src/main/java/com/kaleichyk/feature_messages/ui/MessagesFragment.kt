package com.kaleichyk.feature_messages.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.clear
import coil.load
import com.kaleichyk.core_database.messagesUtils.PaginationLastState
import com.kaleichyk.feature_messages.MessageData
import com.kaleichyk.feature_messages.R
import com.kaleichyk.feature_messages.databinding.FragmentMessagesBinding
import com.kaleichyk.feature_messages.di.MessagesFeatureComponentHolder
import com.kaleichyk.feature_messages.toMessageData
import com.kaleichyk.feature_messages.ui.adapter.MessagesAdapter
import com.kaleichyk.feature_messages.ui.viewModel.MessagesViewModel
import com.kaleichyk.feature_messages.ui.viewModel.ViewModelFactory
import com.kaleichyk.utils.CurrentUser
import com.kaleichyk.utils.MimeTypes.MIME_TYPE_IMAGE
import com.kaleichyk.utils.navigation.NavigationConstants.DIALOG
import com.kaleichyk.utils.navigation.NavigationSystem
import com.kaleichyk.utils.showLog
import com.koleychik.basic_resource.showToast
import com.koleychik.models.Message
import com.koleychik.models.dialog.Dialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MessagesFragment : Fragment(),
    DialogDeleteMessageConfirmation.DialogDeleteMessageConfirmationListener {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    private val adapter = MessagesAdapter()

    private val viewModel: MessagesViewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MessagesViewModel::class.java]
    }

    private val dialog: Dialog by lazy {
        requireArguments().getParcelable(DIALOG) ?: Dialog()
    }

    private val dialogToDeleteMessage by lazy {
        DialogDeleteMessageConfirmation()
    }

    private val selectImage: ActivityResultLauncher<String> =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            showLog("selectImage uri = $uri")
            viewModel.selectedImageUri = uri.toString()
            binding.selectedImage.load(uri.toString()) {
                placeholder(R.drawable.image_placeholder)
                binding.unselectImage.visibility = View.VISIBLE
            }
        }

    private var _binding: FragmentMessagesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        NavigationSystem.onStartFeature?.let { start -> start(this) }
        _binding = FragmentMessagesBinding.inflate(inflater, container, false)
        MessagesFeatureComponentHolder.getComponent().inject(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRV()
        setupSelectedImage()
        setupSubscribeToNewMessages()
        setupSwipeToRefresh()
        updateInterlocutorInfo()
        createOnClickListener()
        subscribe()
        checkFirstData()
    }

    private fun subscribe() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
    }

    private fun getMessages() = lifecycleScope.launch(Dispatchers.Main) {
        viewModel.getMessages(dialog.id)
            .collect { list ->
                showLog("list = $list")
                handleList(list)
            }
    }

    private fun handleList(list: List<MessageData>) {
        if (list.isEmpty() && viewModel.lastState !is PaginationLastState.End) emptyListMessages()
        else showList(list)
    }

    private fun showList(list: List<MessageData>) {
        binding.textNoMessages.visibility = View.GONE
        adapter.addToList(list)
    }

    private fun emptyListMessages() {
        binding.textNoMessages.visibility = View.VISIBLE
    }

    private fun showInfo(text: Int) {
        requireContext().showToast(text)
    }

    private fun checkFirstData() {
        if (viewModel.fullMessagesList == null) getMessages()
        else handleList(viewModel.fullMessagesList!!)
    }

    private fun setupRV() {
        val layoutManager = LinearLayoutManager(requireContext()).apply {
            reverseLayout = true
            stackFromEnd = false
        }
        binding.rv.run {
            this.layoutManager = layoutManager
            adapter = this@MessagesFragment.adapter
        }
        adapter.onItemLongClick = { data ->
            viewModel.messageToDelete = data
            dialogToDeleteMessage.show(childFragmentManager, "Dialog To Delete Message")
        }
    }

    private fun setupSelectedImage() {
        if (viewModel.selectedImageUri == null) {
            binding.unselectImage.visibility = View.GONE
            binding.selectedImage.clear()
        } else {
            binding.unselectImage.visibility = View.VISIBLE
            binding.selectedImage.load(viewModel.selectedImageUri)
        }
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            getMessages()
        }
    }

    private fun updateInterlocutorInfo() {
        with(binding) {
            dialog.receiver.icon.let { uri -> interlocutorIcon.load(uri) }
            interlocutorName.text = dialog.receiver.name
        }
    }

    private fun createOnClickListener() {
        val onClickListener = View.OnClickListener {
            when (it.id) {
                R.id.sendIcon -> sendMessage()
                R.id.chooseImage -> chooseImage()
                R.id.unselectImage -> unselectImage()
            }
        }
        with(binding) {
            sendIcon.setOnClickListener(onClickListener)
            chooseImage.setOnClickListener(onClickListener)
            unselectImage.setOnClickListener(onClickListener)
        }
    }

    private fun deleteMessage() {
        viewModel.messageToDelete?.let {  messageData ->
            adapter.delete(messageData)
            viewModel.deleteMessage(getNewLastMessage(messageData))
        }
    }

    private fun getNewLastMessage(messageToDelete: MessageData): Message? {
        if (dialog.lastMessage?.id != messageToDelete.id) return null
        return try {
            viewModel.fullMessagesList!![1].toMessage()
        } catch (e: Exception) {
            null
        }
    }

    private fun addMessage(messageData: MessageData) {
        adapter.addItem(messageData)
        binding.rv.scrollToPosition(0)
    }

    private fun sendMessage() {
        val text = binding.edtMessageText.text.toString().trim()
        if (text.isEmpty()) {
            showInfo(R.string.write_some_message)
            return
        }
        val message = createMessageData(text)
        addMessage(message)
        viewModel.sendMessage(message.toMessage(), dialog.receiver.id)
        binding.edtMessageText.text.clear()
    }

    private fun unselectImage() {
        viewModel.selectedImageUri = null
        binding.selectedImage.clear()
    }

    private fun chooseImage() {
        if (viewModel.selectedImageUri != null) {
            requireContext().showToast(R.string.image_is_selected)
            return
        }
        try {
            selectImage.launch(MIME_TYPE_IMAGE)
        } catch (e: RuntimeException) {
            requireContext().showToast(e.message.toString())
        }
    }

    private fun setupSubscribeToNewMessages() {
        viewModel.subscribeToNewMessages(dialog.id) { message ->
            if (message.authorId == CurrentUser.user!!.id) return@subscribeToNewMessages
            addMessage(message.toMessageData())
        }
    }

    private fun unsubscribeToNewMessages() {
        viewModel.unsubscribeToNewMessages(dialog.id)
    }

    private fun createMessageData(text: String): MessageData = MessageData.createMessage(
        dialog.id,
        CurrentUser.user!!.id,
        text,
        viewModel.selectedImageUri
    )

    override fun dialogDeleteMessageConfirmationSuccessful() {
        deleteMessage()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unsubscribeToNewMessages()
        _binding = null
    }
}