package com.technopark.youtrader.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.base.EventObserver
import com.technopark.youtrader.databinding.ProfileFragmentBinding
import com.technopark.youtrader.model.AuthState
import com.technopark.youtrader.model.Result
import com.technopark.youtrader.ui.AppActivity
import com.technopark.youtrader.utils.AlertDialogHelper
import com.technopark.youtrader.utils.ImageHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment(R.layout.profile_fragment) {

    private val binding by viewBinding(ProfileFragmentBinding::bind)

    override val viewModel: ProfileViewModel by viewModels()

    private var imageHandler: ImageHandler? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imageHandler = ImageHandler(
            requireActivity().activityResultRegistry,
            viewLifecycleOwner,
            requireContext()
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val photoUri = getStringFromPrefs(ImageHandler.PROFIlE_PHOTO_PREFS_KEY)
            if (photoUri.isNotEmpty()) {
                loadPicture(Uri.parse(photoUri))
            }

            val previousName = getFullNameFromPrefs()
            fullName.text =
                if (previousName == getString(R.string.value_is_not_defined))
                    getString(R.string.new_user_full_name)
                else previousName

            changeNameBtn.setOnClickListener {
                AlertDialogHelper.showOneEditText(
                    layoutInflater,
                    requireContext(),
                    getString(R.string.change_fio),
                    fullName.text.toString()
                ) { newName: String ->
                    fullName.text = newName
                    setFullNameToPrefs(newName)
                }
            }

            portrait.setOnClickListener {
                imageHandler?.getImage { uri ->
                    if (uri != null) {
                        loadPicture(uri)
                        setStringToPrefs(ImageHandler.PROFIlE_PHOTO_PREFS_KEY, uri.toString())
                    }
                }
            }

            takePicture.setOnClickListener {
                imageHandler?.takePicture { uri ->
                    if (uri != null) {
                        loadPicture(uri)
                        setStringToPrefs(ImageHandler.PROFIlE_PHOTO_PREFS_KEY, uri.toString())
                    }
                }
            }

            switchPinCode.isChecked = isPinSet()

            switchPinCode.setOnCheckedChangeListener { _, isChecked ->
                if (!isChecked) {
                    setAuthState(AuthState.PinInactive)
                }
                switchPinCode.isChecked = isChecked
            }
            switchPinCode.setOnClickListener {
                if (switchPinCode.isChecked) {
                    viewModel.navigateToPinRegFragment()
                }
                switchPinCode.isChecked = isPinSet()
            }

            buttonUpdatePassword.setOnClickListener {
                AlertDialogHelper.showOneEditText(
                    layoutInflater,
                    requireContext(),
                    getString(R.string.change_password),
                    "",
                    getString(R.string.new_password)
                ) { newPassword: String ->
                    viewModel.updatePassword(newPassword)
                }
            }

            signOut.setOnClickListener {
                viewModel.signOut()
            }

            viewModel.updatePasswordState.observe(
                viewLifecycleOwner,
                EventObserver { state ->
                    when (state) {
                        is Result.Success -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.password_was_successfully_changed),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        is Result.Error -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.password_changing_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )

            viewModel.signOutState.observe(
                viewLifecycleOwner,
                EventObserver { state ->
                    when (state) {
                        is Result.Success -> {
                            setAuthState(AuthState.NotAuthenticated)
                            viewModel.navigateToAuthFragment()
                        }
                        is Result.Error -> {
                            Toast.makeText(
                                requireContext(),
                                getString(R.string.sign_out_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            )
        }
    }

    private fun loadPicture(uri: Uri) {
        Glide.with(this)
            .load(uri)
            .placeholder(R.drawable.avatar)
            .into(binding.portrait)
    }

    private fun getFullNameFromPrefs(): String {
        return getStringFromPrefs(
            getString(R.string.profile_name_key),
            getString(R.string.value_is_not_defined)
        )
    }

    private fun setFullNameToPrefs(fullName: String) {
        setStringToPrefs(getString(R.string.profile_name_key), fullName)
    }

    private fun isPinSet(): Boolean = (activity as AppActivity).isPinSet()

    override fun onDestroyView() {
        imageHandler?.cleanUnusedPhotos()
        imageHandler = null
        super.onDestroyView()
    }
}
