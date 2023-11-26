package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Node
import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.ast.ASTRef
import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.error.error
import me.alex_s168.sfl.lexer.Token
import me.alex_s168.sfl.lexer.TokenType
import me.alex_s168.sfl.location.tTo

fun parseRef(
    stream: Stream<Token>,
    err: ErrorContext
): Node<ASTRef>? {
    val next = stream.consume()
        ?: return null
    return when (next.type) {
        TokenType.IDENTIFIER -> {
            var expectSep = true
            val tks = mutableListOf(next)
            tks += stream.consumeWhile {
                if (it.type !in setOf(TokenType.DOT, TokenType.NAMESPACE, TokenType.IDENTIFIER)) {
                    false
                } else if (expectSep) {
                    expectSep = false
                    it.type == TokenType.DOT || it.type == TokenType.NAMESPACE
                } else {
                    expectSep = true
                    it.type == TokenType.IDENTIFIER
                }
            }
            if (!expectSep) {
                err.error("Unexpected token (or missing identifier)", tks.last().location)
            }
            Node(ASTRef(tks, tks.first().location tTo tks.last().location))
        }
        else -> null
    }
}
