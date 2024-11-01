package edu.google.compose.databasepracticeroom

import android.app.Application
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room
import edu.google.compose.databasepracticeroom.database.Admin
import edu.google.compose.databasepracticeroom.database.MobileCommerceDB
import kotlinx.coroutines.flow.flow
import java.util.UUID

enum class SecondStepFields {
    USERNAME,
    EMAIL,
    PASSWORD,
    CONFIRMATION_PASSWORD
}

enum class FirstStepFields {
   FIRST_NAME,
   LASTNAME,
   PHONE_NUMBER
}


class SignUpViewModel(application: Application) : AndroidViewModel(application) {
    private val TAG = "SignUpViewModel"
    val flow = flow<Map<String, String>>() {}


    var statusMap: Map<SecondStepFields, String>? = null
    var username = ""
    var password = ""
    var firstName = ""
    var lastname = ""
    var phoneNumber = ""
    var email = ""
    var passwordConfirmation = ""
    var uri: Uri? = null


    // hard coded database is not best following the best practice
    private val db = DatabaseInstance.getInstance(application)


    fun validateFirstPage(): Boolean {
        val regForNames = "[a-zA-Z]{2,}".toRegex()
        val regPhoneNumber = "^[5279][0-9]{7}".toRegex()
        Log.d(TAG, "validateFirstPage: lastname match ${lastname.matches(regForNames) }")
        Log.d(TAG, "validateFirstPage: name match ${firstName.matches(regForNames) }")
        Log.d(TAG, "validateFirstPage: number match ${phoneNumber.matches(regPhoneNumber) }")
        return lastname.matches(regForNames) && firstName.matches(regForNames) && phoneNumber.matches(regPhoneNumber)
    }


    suspend fun validateSecondPage() = flow<Map<String, String>> {
        val emailRegex = """^[a-zA-Z]([a-zA-Z-_\.]*[a-zA-Z0-9])*@[a-zA-Z]+(\.[a-zA-Z]{2,})+""".toRegex()
        val usernameRegex = """[a-zA-Z0-9]{8}""".toRegex()
        val mapError = mutableMapOf<String, String>()

       if(!email.matches(emailRegex)) {
          mapError["email"] = "incorrect email format"
       }
        if(!username.matches(usernameRegex)) {
           mapError["username"] = "username incorrect 8 characters"
        }

        if(password != passwordConfirmation) {
            mapError["password"] = "password confirmation error"
        }
        if(mapError.isEmpty()) {
            val userWithEmail = db.adminDao().getAdminByEmail(email)
            val userWithUserName = db.adminDao().getAdminByUsername(username)

            userWithUserName?.let {
                mapError["username"] = "username already exists"
            }

            userWithEmail?.let {
                mapError["email"] = "email already exits"
            }
        }
        if(mapError.isNotEmpty()) {
            mapError["status"] = "0"
            emit(mapError)
        }else {
            mapError["status"] = "1"
            emit(mapError)
        }
    }


    suspend fun validateThirdPage() = flow<Map<String, String>>{
        val uri = Uri.parse(uri.toString())
        if (uri.toString().startsWith("file")) {
            emit(mapOf("status" to "1"))
            db.adminDao().insertAdmin(
                Admin(
                    username = username,
                    name = firstName,
                    lastname = lastname,
                    phoneNumber = phoneNumber,
                    imageFilePath = uri.toString(),
                    email = email,
                    password = password,
                    id = UUID.randomUUID()
                )
            )
        }else {
            emit(mapOf("status" to "0", "uri" to "same thing went wrong please pick other image"))
        }

    }

}