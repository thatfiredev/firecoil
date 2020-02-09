package io.github.rosariopfernandes.firecoilsampleapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import com.google.firebase.storage.FirebaseStorage
import io.github.rosariopfernandes.firecoil.StorageReferenceFetcher
import io.github.rosariopfernandes.firecoil.get
import io.github.rosariopfernandes.firecoil.load
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    // Declare the views
    private lateinit var toolbar: Toolbar
    private lateinit var tvStatus: TextView
    private lateinit var btnImageViewKtx: Button
    private lateinit var btnGet: Button
    private lateinit var btnLoad: Button
    private lateinit var imageView: ImageView

    private lateinit var imageLoader: ImageLoader

    // TODO(developer): Change this to point to your image's path in Cloud Storage
    private val storageRef = FirebaseStorage.getInstance().reference
        .child("example.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        tvStatus = findViewById(R.id.tvStatus)
        btnImageViewKtx = findViewById(R.id.btnImageViewKtx)
        btnGet = findViewById(R.id.btnGet)
        btnLoad = findViewById(R.id.btnLoad)
        imageView = findViewById(R.id.imageView)

        setSupportActionBar(toolbar)

        btnImageViewKtx.setOnClickListener {
            // Basic usage: Simply call the ImageView.load() extension function
            imageView.load(storageRef)

            // For custom requests, you can pass it a lambda function:
            // imageView.load(storageRef) {
            //     crossfade(true)
            // }
            // ...
            tvStatus.text = getString(R.string.message_image_loaded, "the ImageView KTX")
        }

        // Note that you need to add the StorageReferenceFetcher
        // when loading images through an ImageLoader
        imageLoader = ImageLoader(this) {
            componentRegistry {
                add(StorageReferenceFetcher())
            }
        }

        btnGet.setOnClickListener {
            // Since ImageLoader.get() is a suspend function,
            // it must be called from a Coroutine builder
            lifecycleScope.launch {
                val drawable = imageLoader.get(storageRef) {
                    // Optionally: Add get params here
                }
                imageView.setImageDrawable(drawable)
                tvStatus.text = getString(R.string.message_image_loaded, "ImageLoader.get()")
            }
            // ...
        }

        btnLoad.setOnClickListener {
            val request = imageLoader.load(this, storageRef) {
                // Load into the ImageView
                target(imageView)

                // Load into any other target:
                // target { drawable ->
                //     ...
                // }
            }
            // Optionally, you can cancel the request by calling request.dispose()
            // ...
            tvStatus.text = getString(R.string.message_image_loaded, "ImageLoader.load()")
        }
    }

}
