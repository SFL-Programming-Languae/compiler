package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Node
import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.ast.ASTType
import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.error.error
import me.alex_s168.sfl.lexer.Token
import me.alex_s168.sfl.lexer.TokenType

/**
 * Expects the next token to be after the opening angle bracket.
 */
fun parseAngleBrackets(
    stream: Stream<Token>,
    err: ErrorContext
): MutableList<Node<ASTType>>? {
    val next2 = stream.peek()
        ?: return null
    var level = 1
    val tokens: MutableList<MutableList<Token>> =
        mutableListOf(mutableListOf())
    while (true) {
        val next3 = stream.consume()
            ?: break
        if (next3.type == TokenType.ANGLE_OPEN) {
            level++
            tokens.last().add(next3)
            continue
        } else if (next3.type == TokenType.ANGLE_CLOSE) {
            level--
            if (level == 0) {
                break
            }
            tokens.last().add(next3)
            continue
        } else if (next3.type == TokenType.COMMA && level == 1) {
            tokens.add(mutableListOf())
            continue
        }
        tokens.last().add(next3)
    }
    if (level != 0) {
        err.error("Missing closing angle bracket", next2.location)
    }
    return tokens.mapNotNullTo(mutableListOf()) {
        if (it.isEmpty()) {
            return@mapNotNullTo null
        }
        parseType(Stream(it.asReversed()).setDone(), err)
    }
}