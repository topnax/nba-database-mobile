
package com.github.topnax.nbadatabasemobile.domain.paginator

/**
 * A default implementation of a paginator.
 *
 * DISCLAIMER: Public methods should be called from a single-threaded dispatcher.
 */
class DefaultPaginator<Key, Item>(
    /**
     * The initial key to start loading from
     */
    private val initialKey: Key,

    /**
     * When loading finishes
     */
    private val onLoadUpdated: (Boolean) -> Unit,

    /**
     * Used to request the next page
     */
    private val onRequest: suspend (nextKey: Key) -> Result<Item>,

    /**
     * Returns next key to load from already loaded data
     */
    private val getNextKey: suspend (Item) -> Key,

    /**
     * Invoked with an error when loading fails
     */
    private val onError: suspend (Throwable?) -> Unit,
    /**
     * Invoked when a new page is loaded. Both the new items and the new key are available via the parameters.
     */
    private val onSuccess: suspend (items: Item, newKey: Key) -> Unit,
) : Paginator<Key, Item> {

    private var nextKeyToBeLoaded = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true

        onLoadUpdated(true)

        // Load the next page
        val result = onRequest(nextKeyToBeLoaded)

        isMakingRequest = false

        // Handle the result
        val item = if (result.isSuccess) {
            result.getOrThrow()
        } else {
            onError(result.exceptionOrNull())
            onLoadUpdated(false)
            return
        }

        // Update the paginator state and notify
        nextKeyToBeLoaded = getNextKey(item)
        onSuccess(item, nextKeyToBeLoaded)
        onLoadUpdated(false)
    }

    override fun reset() {
        nextKeyToBeLoaded = initialKey
    }
}