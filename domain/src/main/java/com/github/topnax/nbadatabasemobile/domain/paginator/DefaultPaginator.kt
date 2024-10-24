
package com.github.topnax.nbadatabasemobile.domain.paginator


class DefaultPaginator<Key, Item>(
    private val initialKey: Key,

    /**
     * When loading finishes
     */
    private inline val onLoadUpdated: (Boolean) -> Unit,

    private inline val onRequest: suspend (nextKey: Key) -> Result<Item>,

    /**
     * Returns next key to load from already loaded data
     */
    private inline val getNextKey: suspend (Item) -> Key,

    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: Item, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currentKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems() {
        if (isMakingRequest) {
            return
        }
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currentKey)
        isMakingRequest = false
        val item = if (result.isSuccess) {
            result.getOrThrow()
        } else {
            onError(result.exceptionOrNull())
            onLoadUpdated(false)
            return
        }
        currentKey = getNextKey(item)
        onSuccess(item, currentKey)
        onLoadUpdated(false)
    }

    override fun reset() {
        currentKey = initialKey
    }
}