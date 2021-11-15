package com.technopark.youtrader.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.technopark.youtrader.R
import com.technopark.youtrader.base.BaseFragment
import com.technopark.youtrader.databinding.ProfileFragmentBinding
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
                portrait.setImageURI(
                    Uri.parse(photoUri)
                )
            }

            portrait.setOnClickListener {
                imageHandler?.getImage { uri ->
                    if (uri != null) {
                        portrait.setImageURI(uri)
                        setStringToPrefs(ImageHandler.PROFIlE_PHOTO_PREFS_KEY, uri.toString())
                    }
                }
            }

            takePicture.setOnClickListener {
                imageHandler?.takePicture { uri ->
                    if (uri != null) {
                        portrait.setImageURI(uri)
                        setStringToPrefs(ImageHandler.PROFIlE_PHOTO_PREFS_KEY, uri.toString())
                    }
                }
            }

            buttonBack.setOnClickListener {
                viewModel.navigateToCurrenciesFragment()
            }
            buttonNext.setOnClickListener {
                viewModel.navigateToWithoutBottomNavViewFragment()
            }
        }
    }

    override fun onDestroyView() {
        imageHandler?.cleanUnusedPhotos()
        imageHandler = null
        super.onDestroyView()
    }
}
