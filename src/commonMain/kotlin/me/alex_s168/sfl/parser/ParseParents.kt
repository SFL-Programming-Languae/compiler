package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.lexer.Token
import me.alex_s168.sfl.lexer.TokenType

/**
 * Expects the opening parenthesis to be consumed already.
 */
fun <T> parseParents(
    stream: Stream<Token>,
    err: ErrorContext,
    commasep: Boolean,
    proc: (MutableList<Token>) -> T?
): List<T>? {
    val tokens = mutableListOf<MutableList<Token>>()
    var depth = 1
    val current = mutableListOf<Token>()
    while (true) {
        val next = stream.peek()
            ?: return null

        if (next.type == TokenType.PAREN_OPEN) {
            depth++
        }
        else if (next.type == TokenType.PAREN_CLOSE) {
            depth--
            if (depth == 0) {
                stream.consume()
                tokens.add(current)
                break
            }
        } else if (commasep && depth == 1 && next.type == TokenType.COMMA) {
            stream.consume()
            tokens.add(current)
            current.clear()
            continue
        }

        current.add(next)
        stream.consume()
    }

    return tokens.mapNotNull {
        if (it.isEmpty()) {
            return@mapNotNull null
        }
        proc(it)
    }
}