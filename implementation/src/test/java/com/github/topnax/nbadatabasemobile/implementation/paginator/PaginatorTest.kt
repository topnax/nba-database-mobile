package com.github.topnax.nbadatabasemobile.implementation.paginator
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class DefaultPaginatorTest {

    @Test
    fun `paginator moves to the next page if one is available`() = runTest {
        // Arrange:
        // Mocks and setup
        val onLoadUpdated: (Boolean) -> Unit = mockk(relaxed = true)
        val onRequest = mockk<suspend (String) -> List<String>> {
            coEvery { this@mockk("initialKey") } returns listOf("Item1", "Item2")
            coEvery { this@mockk("nextKey1") } returns listOf("Item3", "Item4")
        }
        val getNextKey = mockk<suspend (List<String>) -> String?> {
            coEvery { this@mockk(listOf("Item1", "Item2")) } returns "nextKey1"
            coEvery { this@mockk(listOf("Item3", "Item4")) } returns null
        }
        val onError: suspend (Throwable?) -> Unit = mockk(relaxed = true)
        val onSuccess = mockk<suspend (List<String>, String?) -> Unit>(relaxed = true)
        val onEndReached = mockk<() -> Unit>(relaxed = true)

        // Create paginator instance
        val paginator = DefaultPaginator(
            initialKey = "initialKey",
            onLoadUpdated = onLoadUpdated,
            onRequest = onRequest,
            getNextKey = getNextKey,
            onError = onError,
            onSuccess = onSuccess,
            onEndReached = onEndReached
        )

        // Act:
        // First load
        paginator.loadNextItems()
        // Second load
        paginator.loadNextItems()
        // Third load
        paginator.loadNextItems()

        // Assert:
        // Verify that after first load, currentKey updates to nextKey1
        coVerifySequence {
            // First load
            onLoadUpdated(true)
            onRequest("initialKey")
            getNextKey(listOf("Item1", "Item2"))
            onSuccess(listOf("Item1", "Item2"), "nextKey1")
            onLoadUpdated(false)

            // Second load
            onLoadUpdated(true)
            onRequest("nextKey1")
            getNextKey(listOf("Item3", "Item4"))
            onSuccess(listOf("Item3", "Item4"), null)
            onLoadUpdated(false)
            onEndReached()

            // Third load (immediately ends)
            onEndReached()
        }
    }
}
