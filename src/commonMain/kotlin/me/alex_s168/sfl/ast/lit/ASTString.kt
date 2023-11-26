package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTString(
    val value: String,
    loc: TokenLocation
): ASTNode("string", loc) {
    override fun toString(): String =
        "ASTString(value=$value, loc=$loc)"
}