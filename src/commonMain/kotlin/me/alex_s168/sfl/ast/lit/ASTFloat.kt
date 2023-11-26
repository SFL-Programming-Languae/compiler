package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTFloat(
    val value: Double,
    loc: TokenLocation
): ASTNode("float", loc) {
    override fun toString(): String =
        "ASTFloat(value=$value, loc=$loc)"
}