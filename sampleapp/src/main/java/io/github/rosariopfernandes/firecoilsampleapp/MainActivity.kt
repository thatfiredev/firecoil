package io.github.rosariopfernandes.firecoilsampleapp

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.request.ErrorResult
import coil.request.SuccessResult
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import io.github.rosariopfernandes.firecoil.FireCoil
import io.github.rosariopfernandes.firecoil.load
import io.github.rosariopfernandes.firecoilsampleapp.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageView: ImageView

    // TODO(developer): Change this to point to your image's path in Cloud Storage
    private val storageRef = Firebase.storage.reference.child("example.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        imageView = binding.imageView

        setSupportActionBar(binding.toolbar)

        binding.btnImageViewKtx.setOnClickListener {
            // Basic usage: Simply call the ImageView.load() extension function
            imageView.load(storageRef)

            // For custom requests, you can pass it a lambda function:
            // imageView.load(storageRef) {
            //     crossfade(true)
            // }
            // ...
            binding.tvStatus.text = getString(R.string.message_image_loaded, "the ImageView KTX")
        }

        binding.btnGet.setOnClickListener {
            // Since ImageLoader.get() is a suspend function,
            // it must be called from a Coroutine builder
            lifecycleScope.launch {
                val result = FireCoil.get(this@MainActivity, storageRef) {
                    // Optionally: Add get params here
                }
                when (result) {
                    is SuccessResult -> {
                        val drawable = result.drawable
                        imageView.setImageDrawable(drawable)
                        binding.tvStatus.text = getString(R.string.message_image_loaded,
                            "ImageLoader.get()")
                    }
                    is ErrorResult -> {
                        binding.tvStatus.text = getString(R.string.error_loading_image,
                            result.throwable.message)
                    }
                }
            }
            // ...
        }

        binding.btnLoad.setOnClickListener {
            val request = FireCoil.load(this, storageRef) {
                // Load into the ImageView
                target(imageView)

                // Load into any other target:
                // target { drawable ->
                //     ...
                // }
            }
            // Optionally, you can cancel the request by calling request.dispose()
            // ...
            binding.tvStatus.text = getString(R.string.message_image_loaded, "ImageLoader.load()")
        }
    }

}
