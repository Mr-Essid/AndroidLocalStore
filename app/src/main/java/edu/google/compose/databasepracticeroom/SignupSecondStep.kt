package edu.google.compose.databasepracticeroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SignupSecondStep : Fragment() {

    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var passwordConfirmation: TextInputEditText
    private val viewModel: SignUpViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_signup_second_step, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        username = view.findViewById(R.id.username_signup)
        email = view.findViewById(R.id.email_signup)
        password = view.findViewById(R.id.password_signup)
        passwordConfirmation = view.findViewById(R.id.password_con_signup)



        username.addTextChangedListener {
           viewModel.username = it.toString()
        }
       email .addTextChangedListener {
            viewModel.email = it.toString()
        }
        password.addTextChangedListener {
            viewModel.password = it.toString()
        }
        passwordConfirmation.addTextChangedListener {
            viewModel.passwordConfirmation = it.toString()
        }
    }

}