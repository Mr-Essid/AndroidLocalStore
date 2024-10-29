package edu.google.compose.databasepracticeroom

import android.annotation.SuppressLint
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toFile
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import edu.google.compose.databasepracticeroom.database.MobileCommerceDB
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.io.FileNotFoundException

class DashboardActivity : AppCompatActivity() {
    lateinit var imageView: ImageView
    lateinit var textView: TextView
    lateinit var recyclerView: RecyclerView
    val viewModel: DashboardViewModel by viewModels()
    lateinit var database: MobileCommerceDB
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_dashboard)
        val username = intent.getStringExtra("username")
        imageView = findViewById(R.id.dashboard_profile_pic)
        textView = findViewById(R.id.username_dashboard)
        recyclerView = findViewById(R.id.dashboard_home_products)
        database = DatabaseInstance.getInstance(applicationContext)
        if(username == null) {
            Toast.makeText(this, "same thing went wrong", Toast.LENGTH_SHORT).show()
            finish()
        }else {
            lifecycleScope.launch {
                viewModel.setViewModelDependency(username).collectLatest {
                 if (it["user"] == 1)
                     viewModel.user?.let { admin ->
                         textView.text = "Welcome ${admin.name}"
                         val uri = Uri.parse(admin.imageFilePath)
                         try {
                             contentResolver.openInputStream(uri).use { inputStream ->
                                 val bytes = inputStream?.readBytes()
                                 bytes?.let { bytes_ ->
                                     imageView.setImageBitmap(
                                         BitmapFactory.decodeByteArray(
                                             bytes_,
                                             0,
                                             bytes_.size
                                         )
                                     )
                                 }
                             }
                         }catch (e: FileNotFoundException) {
                             imageView.setImageResource(R.drawable.profile_pic)
                         }
                         }
                    if (it["products"] == 1) {
                        Log.d("TAG", "onCreate: list rendred ")
                        val arrayOfProducts = viewModel.products.toTypedArray()
                        val adapter = ProductListAdapter(arrayOfProducts)
                        recyclerView.adapter = adapter
                        recyclerView.layoutManager = LinearLayoutManager(this@DashboardActivity)
                    }
                }
            }
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



    }
}