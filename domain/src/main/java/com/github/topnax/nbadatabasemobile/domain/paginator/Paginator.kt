
package com.github.topnax.nbadatabasemobile.domain.paginator

/**
 * A generic interface for paginating items.
 */
interface Paginator<Key, Item> {
    /**
     * Loads the next page with items.
     */
    suspend fun loadNextItems()

    /**
     * Resets the paginator to the initial state
     */
    fun reset()
}