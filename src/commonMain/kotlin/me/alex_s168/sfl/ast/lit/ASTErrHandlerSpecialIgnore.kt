package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTErrHandlerSpecialIgnore(
    loc: TokenLocation
): ASTNode("errhandler: special-ignore", loc)