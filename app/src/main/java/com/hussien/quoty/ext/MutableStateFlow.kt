package com.hussien.quoty.ext

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * inverse the state of this boolean flow
 */
fun MutableStateFlow<Boolean>.inverse() {
    this.value = !value
}