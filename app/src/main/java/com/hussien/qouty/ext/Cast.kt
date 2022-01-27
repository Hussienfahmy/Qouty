package com.hussien.qouty.ext

inline fun <reified T> Any?.doIfTypeIs(block: T.() -> Unit) {
    if (this is T) block()
}