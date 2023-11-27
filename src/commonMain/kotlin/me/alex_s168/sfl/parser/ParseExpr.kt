package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Node
import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.lexer.Token

fun parseExpr(
    stream: Stream<Token>,
    err: ErrorContext
): Node<ASTNode>? {
    val next = stream.peek()
        ?: return null

    return when (next.type) {
        else -> null
    }
}