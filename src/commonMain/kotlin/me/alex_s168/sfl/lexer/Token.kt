package me.alex_s168.sfl.lexer

import me.alex_s168.sfl.location.TokenLocation

data class Token(
    val type: TokenType,
    val location: TokenLocation,
    val value: String? = null // only for identifiers and literals
) {
    override fun toString() =
        "Token($type${if (value != null) ", $value" else ""}, $location)"
}