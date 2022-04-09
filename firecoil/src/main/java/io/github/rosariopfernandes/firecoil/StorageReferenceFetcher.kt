package io.github.rosariopfernandes.firecoil

import coil.ImageLoader
import coil.decode.DataSource
import coil.decode.ImageSource
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.request.Options
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import okio.buffer
import okio.source

class StorageReferenceFetcher(
    private val data: StorageReference,
    private val options: Options
) : Fetcher {

    override suspend fun fetch(): FetchResult {
        val taskSnapshot = data.stream.await()

        return SourceResult(
            dataSource = DataSource.NETWORK,
            source = ImageSource(taskSnapshot.stream.source().buffer(), options.context),
            mimeType = null
        )
    }

    class Factory : Fetcher.Factory<StorageReference> {

        override fun create(data: StorageReference, options: Options, imageLoader: ImageLoader): Fetcher {
            return StorageReferenceFetcher(data, options)
        }
    }
}
