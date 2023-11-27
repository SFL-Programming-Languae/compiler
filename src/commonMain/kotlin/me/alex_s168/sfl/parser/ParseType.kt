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
                stream.consume()
                val types = mutableListOf(ident as Node<ASTNode>)
                val types2 = parseAngleBrackets(stream, err)
                types2?.forEach {
                    types += it as Node<ASTNode>
                }
                return Node(ASTType(false, false,
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