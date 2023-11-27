package me.alex_s168.sfl.ast.lit

import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.location.TokenLocation

class ASTErrHandlerPassUp(
    loc: TokenLocation
): ASTNode("errhandler: pass-up", loc)