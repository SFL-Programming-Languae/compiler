package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

/**
 * First child is identifier, all other children are arguments.
 */
class ASTFunCall(
    location: TokenLocation
): ASTNode("funcall", location)