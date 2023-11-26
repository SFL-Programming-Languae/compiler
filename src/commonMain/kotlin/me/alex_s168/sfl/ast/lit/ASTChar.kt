package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTChar(
    val value: Char,
    loc: TokenLocation
): ASTNode("char", loc) {
    override fun toString(): String =
        "ASTChar(value=$value, loc=$loc)"
}