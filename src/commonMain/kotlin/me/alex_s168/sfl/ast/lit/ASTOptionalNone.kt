package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTOptionalNone(
    loc: TokenLocation
): ASTNode("opt_none", loc) {
    override fun toString(): String {
        return "ASTOptionalNone"
    }
}