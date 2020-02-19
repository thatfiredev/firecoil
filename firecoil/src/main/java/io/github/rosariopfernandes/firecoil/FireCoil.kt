package io.github.rosariopfernandes.firecoil

import android.content.Context
import android.graphics.drawable.Drawable
import coil.ImageLoader
import coil.api.getAny
import coil.api.loadAny
import coil.request.GetRequestBuilder
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
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
        builder: LoadRequestBuilder.() -> Unit = {}
    ): RequestDisposable = loader(context).load(context, storageRef, builder)

    suspend fun get(
        context: Context,
        storageRef: StorageReference,
        builder: GetRequestBuilder.() -> Unit = {}
    ): Drawable = loader(context).get(storageRef, builder)

    fun loadAny(
        context: Context,
        data: Any?,
        builder: LoadRequestBuilder.() -> Unit = {}
    ): RequestDisposable = loader(context).loadAny(context, data, builder)

    suspend fun getAny(
        context: Context,
        data: Any,
        builder: GetRequestBuilder.() -> Unit = {}
    ): Drawable = loader(context).getAny(data, builder)

    @Synchronized
    private fun buildDefaultImageLoader(context: Context): ImageLoader {
        // Check again in case imageLoader was just set.
        imageLoader?.let { return it }

        // Create a new ImageLoader.
        val loader = ImageLoader(context) {
            componentRegistry {
                add(StorageReferenceFetcher())
            }
        }
        setDefaultImageLoader(loader)
        return loader
    }
}