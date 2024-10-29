package edu.google.compose.databasepracticeroom

import android.app.Application
import android.os.UserHandle
import android.provider.ContactsContract.Data
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Database
import androidx.room.Room
import edu.google.compose.databasepracticeroom.database.Admin
import edu.google.compose.databasepracticeroom.database.Category
import edu.google.compose.databasepracticeroom.database.MobileCommerceDB
import edu.google.compose.databasepracticeroom.database.Product
import edu.google.compose.databasepracticeroom.database.Tag
import edu.google.compose.databasepracticeroom.database.TagProduct
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.UUID

class DashboardViewModel(application: Application): AndroidViewModel(application) {
    private val databaseInstance = DatabaseInstance.getInstance(application)

    var user: Admin? = null
    val products: MutableList<TagProduct> = mutableListOf()


    fun setViewModelDependency(username: String) = flow<Map<String, Int>> {

        val currentUser = databaseInstance.adminDao().getAdminByUsernameOrEmail(username)

        if (currentUser != null) {
            user = currentUser
            emit(mapOf("user" to 1))
            val currentProductWithTags = databaseInstance.productDao().productsWithTagsOfUser(currentUser.id)
            products.addAll(currentProductWithTags)
            emit(mapOf("products" to 1))
        }
        else
            emit(mapOf("user" to 0))
    }

}