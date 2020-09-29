package io.github.rosariopfernandes.firecoil

import android.content.Context
import coil.ImageLoader
import coil.request.Disposable
import coil.request.ImageRequest
import coil.request.ImageResult
import com.google.firebase.storage.StorageReference

object FireCoil {

    private var imageLoader: ImageLoader? = null

    /**
     * Get the default [ImageLoader] instance. Creates a new instance if none has been set.
     */
    @JvmStatic
    fun loader(context: Context): ImageLoader = imageLoader ?: buildDefaultImageLoader(context)

    /**
     * Set the default [ImageLoader] instance. Shutdown the current instance.
     */
    @JvmStatic
    fun setDefaultImageLoader(loader: ImageLoader) {
        imageLoader?.shutdown()
        imageLoader = loader
    }

    fun load(
        context: Context,
        storageRef: StorageReference,
        builder: ImageRequest.Builder.() -> Unit = {}
    ): Disposable = loader(context).load(context, storageRef, builder)

    suspend fun get(
        context: Context,
        storageRef: StorageReference,
        builder: ImageRequest.Builder.() -> Unit = {}
    ): ImageResult = loader(context).get(context, storageRef, builder)

    fun loadAny(
        context: Context,
        data: Any?,
        builder: ImageRequest.Builder.() -> Unit = {}
    ): Disposable {
        val loadRequest = ImageRequest.Builder(context)
            .data(data)
            .apply(builder)
            .build()
        return loader(context).enqueue(loadRequest)
    }

    suspend fun getAny(
        context: Context,
        data: Any,
        builder: ImageRequest.Builder.() -> Unit = {}
    ): ImageResult {
        val getRequest = ImageRequest.Builder(context)
            .data(data)
            .apply(builder)
            .build()
        return loader(context).execute(getRequest)
    }

    @Synchronized
    private fun buildDefaultImageLoader(context: Context): ImageLoader {
        // Check again in case imageLoader was just set.
        imageLoader?.let { return it }

        // Create a new ImageLoader.
        val loader = ImageLoader.Builder(context)
            .apply {
                componentRegistry {
                    add(StorageReferenceFetcher())
                }
            }.build()
        setDefaultImageLoader(loader)
        return loader
    }
}