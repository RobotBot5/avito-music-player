package com.robotbot.avito.music_api.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.merge

fun <T> Flow<T>.mergeWith(another: Flow<T>): Flow<T> {
    return merge(this, another)
}

fun <T1, T2, T3, R> Flow<T1>.combineTriple(
    another1: Flow<T2>,
    another2: Flow<T3>,
    transform: suspend (T1, T2, T3) -> R
): Flow<R> {
    return this.combine(another1) { t1, t2 -> t1 to t2 }
        .combine(another2) { (t1, t2), t3 -> transform(t1, t2, t3) }
}
