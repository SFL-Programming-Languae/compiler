package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTBool(
    val value: Boolean,
    loc: TokenLocation
): ASTNode("bool", loc) {
    override fun toString(): String =
        "ASTBool(value=$value, loc=$loc)"
}