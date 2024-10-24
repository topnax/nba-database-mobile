
package com.github.topnax.nbadatabasemobile.domain.paginator

interface Paginator<Key, Item> {
    suspend fun loadNextItems()
    fun reset()
}