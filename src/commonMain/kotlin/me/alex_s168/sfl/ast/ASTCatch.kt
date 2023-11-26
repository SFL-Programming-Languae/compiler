package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

class ASTCatch(loc: TokenLocation):
    ASTNode("Catch", loc)