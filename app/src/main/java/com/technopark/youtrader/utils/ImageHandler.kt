package com.technopark.youtrader.utils

import android.content.Context
import android.net.Uri
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.technopark.youtrader.BuildConfig
import java.io.File

class ImageHandler(
    activityResultRegistry: ActivityResultRegistry,
    private val lifecycleOwner: LifecycleOwner,
    private val applicationContext: Context,
) {

    private var getImageCallback: ((imageUri: Uri?) -> Unit)? = null

    private var takePictureCallback: ((imageUri: Uri?) -> Unit)? = null

    private var latestTmpUri: Uri? = null
    private var latestTmpFileName: String? = null

    private val createdFiles: MutableList<String> = mutableListOf()

    private fun getTmpFileUri(): Uri {
        val tmpFile =
            File.createTempFile("tmp_image_file", ".png", applicationContext.cacheDir).apply {
                createNewFile()
            }
        createdFiles.add(tmpFile.toString())
        latestTmpFileName = tmpFile.toString()

        return FileProvider.getUriForFile(
            applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    private val openPhoto: ActivityResultLauncher<Array<String>> =
        activityResultRegistry.register(
            GET_IMAGE_REGISTRY_KEY,
            lifecycleOwner,
            ActivityResultContracts.OpenDocument()
        ) { imageUri: Uri? ->
            getImageCallback?.let { callback -> callback(imageUri) }
        }

    private val takeImageResult: ActivityResultLauncher<Uri> =
        activityResultRegistry.register(
            TAKE_PICTURE_REGISTRY_KEY,
            lifecycleOwner,
            ActivityResultContracts.TakePicture()
        ) { isSuccess ->
            if (isSuccess) {
                latestTmpUri?.let { uri ->
                    takePictureCallback?.let { callback -> callback(uri) }
                }
            }
        }

    fun getImage(callback: (imageUri: Uri?) -> Unit) {
        getImageCallback = callback
        openPhoto.launch(arrayOf("image/*"))
    }

    fun takePicture(callback: (imageUri: Uri?) -> Unit) {
        takePictureCallback = callback
        lifecycleOwner.lifecycleScope.launchWhenStarted {
            getTmpFileUri().let { uri ->
                latestTmpUri = uri
                takeImageResult.launch(uri)
            }
        }
    }

    fun cleanUnusedPhotos() {
        for (fileName in createdFiles) {
            if (fileName != latestTmpFileName) {
                File(fileName).delete()
            }
        }
    }

    companion object {
        private const val GET_IMAGE_REGISTRY_KEY = "ImageHandlerGetImage"
        private const val TAKE_PICTURE_REGISTRY_KEY = "ImageHandlerTakePicture"
        const val PROFIlE_PHOTO_PREFS_KEY = "profile_photo_uri"
    }
}
