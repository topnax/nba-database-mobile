package com.github.topnax.nbadatabasemobile.data

data class Page<Key, Value> (
    val data: Value,
    val nextCursor: Key?
)