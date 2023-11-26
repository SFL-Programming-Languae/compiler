package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Node
import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.ast.ASTType
import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.error.error
import me.alex_s168.sfl.lexer.Token
import me.alex_s168.sfl.lexer.TokenType
import me.alex_s168.sfl.location.tTo

fun parseType(
    stream: Stream<Token>,
    err: ErrorContext
): Node<ASTType>? {
    val next = stream.peek()
        ?: return null
    return when (next.type) {
        TokenType.KW_MUT -> {
            stream.consume()
            Node(ASTType(false, true, next.location), parseType(stream, err)!!.children)
        }
        TokenType.UNDERSCORE -> {
            stream.consume()
            Node(ASTType(true, false, next.location))
        }
        TokenType.IDENTIFIER -> {
            val ident = parseRef(stream, err)!!
            val next2 = stream.peek()
                ?: return Node(ASTType(false, false,
                    ident.value!!.parts.first().location tTo
                            ident.value!!.parts.last().location
                ), mutableListOf(ident as Node<ASTNode>)) as Node<ASTType>
            if (next2.type == TokenType.ANGLE_OPEN) {
                var level = 0
                val tokens: MutableList<MutableList<Token>> =
                    mutableListOf(mutableListOf())
                stream.consume()
                while (true) {
                    val next3 = stream.consume()
                        ?: break
                    if (next3.type == TokenType.ANGLE_OPEN) {
                        level++
                        continue
                    } else if (next3.type == TokenType.ANGLE_CLOSE) {
                        level--
                        if (level < 0) {
                            break
                        }
                        continue
                    } else if (next3.type == TokenType.COMMA && level == 0) {
                        tokens.add(mutableListOf())
                        continue
                    }
                    tokens.last().add(next3)
                }
                if (level != 0) {
                    err.error("Missing closing angle bracket", next2.location)
                }
                val types = mutableListOf(ident as Node<ASTNode>)
                tokens.mapNotNullTo(types) {
                    if (it.isEmpty()) {
                        return@mapNotNullTo null
                    }
                    parseType(Stream(it.asReversed()), err)!! as Node<ASTNode>
                }
                Node(ASTType(false, false,
                    types.first().value!!.loc tTo
                            types.last().value!!.loc
                ), types) as Node<ASTType>
            }
            Node(ASTType(false, false,
                ident.value!!.parts.first().location tTo
                        ident.value!!.parts.last().location
            ), mutableListOf(ident as Node<ASTNode>)) as Node<ASTType>
        }
        else -> {
            err.error("Unexpected token! Expected type", next.location)
            null
        }
    }
}