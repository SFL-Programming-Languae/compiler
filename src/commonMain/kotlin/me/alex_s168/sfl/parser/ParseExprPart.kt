package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Node
import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.ast.ASTCatch
import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.ast.ASTOr
import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.lexer.Token
import me.alex_s168.sfl.lexer.TokenType
import me.alex_s168.sfl.location.tTo

fun parseExprPart(
    stream: Stream<Token>,
    err: ErrorContext
): Node<ASTNode>? {
    val x = parseExprSubPart(stream, err)
        ?: return null

    val next = stream.peek()
        ?: return x

    return when (next.type) {
        TokenType.KW_CATCH -> {
            stream.consume()
            val children = mutableListOf(x)
            val catch = parseExprSubPart(stream, err)
                ?: return null
            children.add(catch)
            Node(ASTCatch(x.value!!.loc tTo catch.value!!.loc), children)
        }
        TokenType.KW_OR -> {
            stream.consume()
            val children = mutableListOf(x)
            val or = parseExpr(stream, err)
                ?: return null
            children.add(or)
            Node(ASTOr(x.value!!.loc tTo or.value!!.loc), children)
        }
        else -> x
    }
}