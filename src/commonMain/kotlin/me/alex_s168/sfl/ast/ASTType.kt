package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

class ASTType(
    val infer: Boolean,
    val mutable: Boolean,
    loc: TokenLocation,
): ASTNode("type", loc) {
    override fun toString(): String =
        "ASTType(infer=$infer, mutable=$mutable, loc=$loc)"
}