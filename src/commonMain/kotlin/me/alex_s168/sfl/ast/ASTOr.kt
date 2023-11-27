package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

/**
 * An expression for error handling.
 * example:   a() or b
 * First child is the expression to be executed, second child is the expression to be executed if the first one fails.
 */
class ASTOr(
    loc: TokenLocation
): ASTNode("or", loc)