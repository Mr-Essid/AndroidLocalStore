package edu.google.compose.databasepracticeroom

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toFile
import androidx.fragment.app.activityViewModels
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

class ProfilePhotoFragment : Fragment(R.layout.fragment_profile_photo) {

    lateinit var imageView: ImageView
    val viewModel: SignUpViewModel by activityViewModels()
    private val contractOfImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        if(it == null) {
            imageView.setImageResource(R.drawable.profile_pic)

            viewModel.uri = null
        }else {
            imageView.setImageURI(it)
            viewModel.uri = it

        }
    }
    private val TAG = "ProfilePhotoFragment"
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageView = view.findViewById(R.id.profile_pic)
        imageView.setOnClickListener {
            contractOfImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            Log.d(TAG, "onViewCreated: pick image")

        }

    }
}