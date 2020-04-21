package io.github.rosariopfernandes.firecoil

import android.graphics.Bitmap
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import coil.bitmappool.BitmapPool
import coil.decode.DataSource
import coil.decode.Options
import coil.fetch.SourceResult
import coil.request.CachePolicy
import coil.request.Parameters
import coil.size.PixelSize
import coil.size.Scale
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StreamDownloadTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.TestCoroutineDispatcher
import okhttp3.Headers
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import java.io.ByteArrayInputStream

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class StorageReferenceFetcherTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var mainDispatcher: TestCoroutineDispatcher
    private lateinit var pool: BitmapPool
    private val options = Options (
        config = Bitmap.Config.ARGB_8888,
        colorSpace = null,
        scale = Scale.FILL,
        allowInexactSize = false,
        allowRgb565 = false,
        headers = Headers.Builder().build(),
        parameters = Parameters.Builder().build(),
        networkCachePolicy = CachePolicy.ENABLED,
        diskCachePolicy = CachePolicy.ENABLED,
        memoryCachePolicy = CachePolicy.ENABLED
    )

    @Before
    fun before() {
        mainDispatcher = TestCoroutineDispatcher().apply { Dispatchers.setMain(this) }
        pool = BitmapPool(0)
    }

    @After
    fun after() {
        Dispatchers.resetMain()
    }

    @Test
    fun `basic firebase storage fetch`() {
        val storageReference = mock(StorageReference::class.java)
        val mockSnapshot = mock(StreamDownloadTask.TaskSnapshot::class.java)
        val task = mock(StreamDownloadTask::class.java)

        val mockStream = ByteArrayInputStream("test".toByteArray())

        `when`(storageReference.stream).thenReturn(task)
        `when`(storageReference.path).thenReturn("hello")
        `when`(task.isComplete).thenReturn(true)
        `when`(task.isCanceled).thenReturn(false)
        `when`(task.result).thenReturn(mockSnapshot)

        val fetcher = StorageReferenceFetcher()
        assertTrue(fetcher.handles(storageReference))
        assertEquals(storageReference.path, fetcher.key(storageReference))

        val result = runBlocking {
            `when`(storageReference.stream.await()).thenReturn(mockSnapshot)
            `when`(mockSnapshot.stream).thenReturn(mockStream)

            fetcher.fetch(pool, storageReference, PixelSize(100, 100), options)
        }
        assertTrue(result is SourceResult)
        if (result is SourceResult) {
            assertTrue(result.dataSource == DataSource.NETWORK)
            assertTrue(result.mimeType == null)
        }
    }
}
