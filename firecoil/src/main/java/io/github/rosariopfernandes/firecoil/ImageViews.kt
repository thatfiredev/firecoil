package io.github.rosariopfernandes.firecoil

import android.widget.ImageView
import coil.request.LoadRequest
import coil.request.LoadRequestBuilder
import coil.request.RequestDisposable
import com.google.firebase.storage.StorageReference

inline fun ImageView.load(
    data: StorageReference,
    builder: LoadRequestBuilder.() -> Unit = {}
): RequestDisposable {
    val loadRequest = LoadRequest.Builder(context).data(data).target(this@load)
        .apply(builder).build()
    return FireCoil.loader(context).execute(loadRequest)
}
