package edu.google.compose.databasepracticeroom

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class SignupActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var cancelButton: Button
    lateinit var signInButton: Button
    private val TAG = "SignupActivity"
    val viewModel: SignUpViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            val ime = insets.getInsets(WindowInsetsCompat.Type.ime())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            v.setPadding(ime.left, ime.top, ime.right, ime.bottom)
            insets
        }

        submitButton = findViewById(R.id.submit_signup)
        cancelButton = findViewById(R.id.cancel_signup)
        signInButton = findViewById(R.id.signin)

        supportFragmentManager.beginTransaction()
            .add(R.id.signup_fragment, SignupFirstStep())
            .commit()


        submitButton.setOnClickListener { _ ->
            Log.d(TAG, "onCreate: current fragment is first ?: ${supportFragmentManager.findFragmentById(R.id.signup_fragment) is SignupFirstStep}" )
            if (supportFragmentManager.findFragmentById(R.id.signup_fragment) is SignupFirstStep) {
                Log.d(TAG, "onCreate we are in first step")

                if(viewModel.validateFirstPage()) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.signup_fragment, SignupSecondStep())
                        .commit()
                }
            }

            if(supportFragmentManager.findFragmentById(R.id.signup_fragment) is SignupSecondStep) {

              lifecycleScope.launch {
                  viewModel.validateSecondPage().collectLatest {
                      if (it["status"] == "1") {
                          supportFragmentManager.beginTransaction().replace(R.id.signup_fragment, ProfilePhotoFragment()).commit()
                      }else {
                         val stringBuilder = StringBuilder()
                         it.forEach { (key, value) ->
                            stringBuilder.append("$key: $value\n")
                         }
                          Toast.makeText(this@SignupActivity, stringBuilder.toString(), Toast.LENGTH_SHORT).show()
                      }
                  }
              }
            }
            if(supportFragmentManager.findFragmentById(R.id.signup_fragment) is ProfilePhotoFragment) {
                val internalAppFS = this.filesDir
                val directory = File(internalAppFS, viewModel.username)
                if(!directory.exists()) {
                    directory.mkdir()
                    Log.d(TAG, "ProfileDirectory: isCreated")
                }else {
                    Log.d(TAG, "ProfileDirectory: Exists")
                }

                val newFile = File(directory, "profile_pic.png")
                newFile.outputStream().use { out ->
                    viewModel.uri?.let { uri ->
                        contentResolver.openInputStream(uri).use {
                          val bytes_ = it?.readBytes()
                          newFile.outputStream().use { output ->
                              output.write(bytes_)
                              output.flush()
                          }
                       }
                    }
                }
                viewModel.uri = newFile.toUri()
                lifecycleScope.launch {
                    viewModel.validateThirdPage().collectLatest {
                        if (it["status"] == "1") {
                            setResult(127, Intent().putExtra("status", 0))
                            finish()
                        }
                    }
                }
            }
        }


    }
}