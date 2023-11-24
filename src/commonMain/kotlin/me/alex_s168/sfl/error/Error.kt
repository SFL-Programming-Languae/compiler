package me.alex_s168.sfl.error

import me.alex_s168.sfl.location.TokenLocation

data class Error(
    val message: String,
    val location: TokenLocation,
    val severity: Severity,
)