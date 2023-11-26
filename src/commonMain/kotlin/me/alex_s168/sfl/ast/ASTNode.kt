package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

open class ASTNode(
    val id: String,
    val loc: TokenLocation
) {
    override fun toString(): String {
        return "ASTNode($id, $loc)"
    }
}