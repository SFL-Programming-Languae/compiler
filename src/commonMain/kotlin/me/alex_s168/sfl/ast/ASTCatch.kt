package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

/**
 * First child is the code, second child is the error handler.
 * Represents both catch as expression and catch after a `do` block.
 */
class ASTCatch(loc: TokenLocation):
    ASTNode("Catch", loc)