package edu.google.compose.databasepracticeroom

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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

class SignupActivity : AppCompatActivity() {
    lateinit var submitButton: Button
    lateinit var cancelButton: Button
    lateinit var signInButton: Button
    private val TAG = "SignupActivity"
    val viewModel: SignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        submitButton = findViewById(R.id.submit_signup)
        cancelButton = findViewById(R.id.cancel_signup)
        signInButton = findViewById(R.id.signin)

        supportFragmentManager.beginTransaction()
            .add(R.id.signup_fragment, SignupFirstStep())
            .addToBackStack(null)
            .commit()


        submitButton.setOnClickListener { _ ->
            Log.d(TAG, "onCreate: current fragment is first ?: ${supportFragmentManager.findFragmentById(R.id.signup_fragment) is SignupFirstStep}" )
            if (supportFragmentManager.findFragmentById(R.id.signup_fragment) is SignupFirstStep) {
                Log.d(TAG, "onCreate we are in first step")

                if(viewModel.validateFirstPage()) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.signup_fragment, SignupSecondStep())
                        .addToBackStack(null)
                        .commit()
                }
            }

            if(supportFragmentManager.findFragmentById(R.id.signup_fragment) is SignupSecondStep) {
                lifecycle.coroutineScope.launch {
                    viewModel.validateSecondPage().collectLatest { ourMap ->
                        if (ourMap["status"] == "0") {
                            val message = StringBuilder("incorrect: ")
                            Log.d(TAG, "onCreate: Problem With In Your Input Data")
                            ourMap["username"]?.let {
                                message.append(" $it")
                            }
                            ourMap["email"]?.let {
                                message.append(" $it")
                            }
                            Toast.makeText(this@SignupActivity, message.toString(), Toast.LENGTH_SHORT).show()
                        }else {
                            val intent = Intent(this@SignupActivity, MainActivity::class.java)
                            intent.putExtra("status", 1 )
                            setResult(127, intent)
                            finish()
                        }
                    }
                }
            }
        }


    }
}