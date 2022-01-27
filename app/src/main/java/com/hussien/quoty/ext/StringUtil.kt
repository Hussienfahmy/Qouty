package com.hussien.quoty.ext

fun String.trimAllExtraSpaces() =
    split(" ").joinToString(separator = " ") { it.trim() }

fun String.capitalizeEveryFirstChar() =
    split(" ").joinToString(separator = " ") { it.replaceFirstChar { it.uppercase() } }
