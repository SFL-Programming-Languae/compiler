package me.alex_s168.sfl.ast

import me.alex_s168.sfl.lexer.Token
import me.alex_s168.sfl.location.TokenLocation

/**
 * Type arguments are in child nodes.
 */
class ASTRef(
    val parts: List<Token>,
    loc: TokenLocation
): ASTNode("varref", loc) {
    override fun toString(): String =
        "ASTVarRef(parts=$parts, loc=$loc)"
}