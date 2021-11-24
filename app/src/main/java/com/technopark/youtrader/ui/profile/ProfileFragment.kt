package com.technopark.youtrader.ui.profile

import android.app.AlertDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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

            fullName.text = getFullNameFromPrefs()

            changeNameBtn.setOnClickListener {
                val inflater = layoutInflater
                val dialogLayout = inflater.inflate(R.layout.change_name_dialog,null)
                val editFullName  = dialogLayout.findViewById<EditText>(R.id.edit_full_name)
                editFullName.setText(fullName.text)
                AlertDialog.Builder(context)
                    .setTitle("Изменить ФИО")
                    .setView(dialogLayout)
                    .setPositiveButton("OK") { _, _ ->
                        if (editFullName.text.toString().isNotEmpty()) {
                            fullName.text = editFullName.text.toString()
                            setFullNameToPrefs(editFullName.text.toString())
                        }
                     }
                    .create().show()
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
        }
    }

    private fun getFullNameFromPrefs(): String {
        return getStringFromPrefs("full_name","undefined")
    }

    private fun setFullNameToPrefs(fullName : String) {
        setStringToPrefs("full_name",fullName)
    }

    override fun onDestroyView() {
        imageHandler?.cleanUnusedPhotos()
        imageHandler = null
        super.onDestroyView()
    }
}
