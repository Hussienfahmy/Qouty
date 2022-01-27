package com.hussien.quoty.ext

inline fun <reified T> Any?.doIfTypeIs(block: T.() -> Unit) {
    if (this is T) block()
}