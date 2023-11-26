package me.alex_s168.sfl.ast

import me.alex_s168.sfl.location.TokenLocation

class ASTBlock(loc: TokenLocation):
    ASTNode("Code block", loc)