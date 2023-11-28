package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

/**
 * First child is the constructor function call
 */
class ASTNewObj(
    val mut: Boolean,
    loc: TokenLocation
): ASTNode("new-obj", loc) {
    override fun toString(): String =
        "ASTNewObj(mut=$mut, loc=$loc)"
}