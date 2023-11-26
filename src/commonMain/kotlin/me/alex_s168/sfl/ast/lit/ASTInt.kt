package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTInt(
    val value: Long,
    loc: TokenLocation
): ASTNode("int", loc) {
    override fun toString(): String =
        "ASTInt(value=$value, loc=$loc)"
}