package io.github.rosariopfernandes.firecoil

import coil.bitmappool.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.FetchResult
import coil.fetch.Fetcher
import coil.fetch.SourceResult
import coil.size.Size
import com.google.firebase.storage.StorageException
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import okio.buffer
import okio.source

class StorageReferenceFetcher : Fetcher<StorageReference> {

    override suspend fun fetch(
        pool: BitmapPool,
        data: StorageReference,
        size: Size,
        options: Options
    ): FetchResult {
        val source = data.stream.await()

        var mimeType: String?

        try {
            val metadata = data.metadata.await()
            mimeType = metadata.contentType
        } catch (e: StorageException) {
            mimeType = null
        }

        return SourceResult(
            dataSource = DataSource.NETWORK,
            source = source.stream.source().buffer(),
            mimeType = mimeType
        )
    }

    override fun key(data: StorageReference) = data.path

    override fun handles(data: StorageReference): Boolean {
        // TODO: Return true if the StorageReference points to a file
        return true
    }
}
