package com.robotbot.avito.common

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.scan

fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}

fun <T1, T2, T3, R> Flow<T1>.combineTriple(
    another1: Flow<T2>,
    another2: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> {
    return this.combine(another1) { t1, t2 -> t1 to t2 }
        .combine(another2) { (t1, t2), t3 -> transform(t1, t2, t3) }
}