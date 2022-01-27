package com.hussien.qouty.ext

import kotlinx.coroutines.flow.MutableStateFlow

/**
 * inverse the state of this boolean flow
 */
fun MutableStateFlow<Boolean>.inverse() {
    this.value = !value
}