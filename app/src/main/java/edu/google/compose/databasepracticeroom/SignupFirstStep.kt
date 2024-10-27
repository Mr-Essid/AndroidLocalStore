package edu.google.compose.databasepracticeroom
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import com.google.android.material.textfield.TextInputEditText

class SignupFirstStep : Fragment() {

    private lateinit var firstnameEditText: TextInputEditText
    private lateinit var lastnameEditText: TextInputEditText
    private lateinit var phoneNumber: TextInputEditText
    private val viewModel: SignUpViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signup_first_step, container, false)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        firstnameEditText = view.findViewById(R.id.firstname_signup)
        lastnameEditText = view.findViewById(R.id.lastname_signup)
        phoneNumber = view.findViewById(R.id.phone_number_signup)

        firstnameEditText.addTextChangedListener { watcher ->
            viewModel.firstName = watcher.toString()
        }

        lastnameEditText.addTextChangedListener { watcher ->
            viewModel.lastname = watcher.toString()
        }
        phoneNumber.addTextChangedListener { watcher ->
            viewModel.phoneNumber = watcher.toString()
        }
    }
}