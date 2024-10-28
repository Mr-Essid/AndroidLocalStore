package edu.google.compose.databasepracticeroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import androidx.room.RoomDatabase
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import edu.google.compose.databasepracticeroom.database.MobileCommerceDB
import kotlinx.coroutines.launch


// admin (id: Int | UUID, firstname: String, lastname: String, email: String => index(unique,), phone_number: String?, created_at: DateTime, updated_at: DateTime)
// admin.product(id: Int | UUID, label: String, description: String, image: BLOB, created_at: Datetime, updated_at: Datetime)
// product.tag(id: Int | UUID, label: String, description: String, created_at: Datetime, updated_at: Datetime)
// category(id: Int | UUID, label: String, description: String, created_at: Datetime, updated_at: Datetime)

// admin - product => one to many => admin have many products | products belong to admin
// product - tag => many to many => tag has multiple products, product has multiple tags
// category - product => one to many => category has multiple products and product belong to category

// fragments: Home => charts about my products
//            Products => list of products
//            Profile => Personal Infos
//            Login => interface of authentication


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"
    // username
    private lateinit var editTextInputLayout: TextInputLayout
    private lateinit var editText: TextInputEditText

    private val activityLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if(it.resultCode == 127 && it.data?.getIntExtra("status", 0) == 1) {
            Toast.makeText(this, "your account has been registered with success status", Toast.LENGTH_SHORT).show()
        }
    }

    // password
    private lateinit var passwordEditText: TextInputEditText
    private lateinit var passwordEditTextInputLayout: TextInputLayout

    // submit button
    private lateinit var submitButton: Button
    private lateinit var cancelButton: Button

    private lateinit var signUpButton: Button
    private lateinit var db: MobileCommerceDB



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        editTextInputLayout = findViewById(R.id.username_input_layout)
        editText = findViewById(R.id.username_input_edit_text)

        passwordEditText = findViewById(R.id.password_input_edit_text)
        passwordEditTextInputLayout = findViewById(R.id.password_input_layout)
        signUpButton = findViewById(R.id.go_to_signup)
        submitButton = findViewById(R.id.submit_button)
        cancelButton = findViewById(R.id.cancel_button)
        db = Room.databaseBuilder(applicationContext, MobileCommerceDB::class.java, "mainDB").build()

        submitButton.setOnClickListener {
            editText.error = null
            passwordEditText.error = null
            val password = passwordEditText.text.toString()
            val usernameOrEmail = editText.text.toString()

            val usernameIsValid = usernameOrEmail.matches("[a-zA-Z0-9]{8}".toRegex()) or usernameOrEmail.matches("[a-zA-Z][a-zA-Z0-9_-]*@[a-zA-Z-_]{2,}(.[a-zA-Z0-9])+".toRegex())
            if (!usernameIsValid) {
                Log.d(TAG, "OnCreate: Username Incorrect")
                editText.error = "Username Invalid"
            }else {
                lifecycle.coroutineScope.launch {
                    val user = db.adminDao().getAdminByUsernameOrEmail(usernameOrEmail)
                    if (user == null) {
                        Log.d(TAG, "OnCreate: username not exits")
                        editText.error = "username or password incorrect"
                    }else {
                        if(user.password != password) {
                            Log.d(TAG, "OnCreate: username exists but password incorrect")
                            editText.error = "username or password incorrect"
                        }
                        Log.d(TAG, "OnCreate: username exists  password correct")
                    }
                }
            }
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            activityLauncher.launch(intent)
        }

    }



}