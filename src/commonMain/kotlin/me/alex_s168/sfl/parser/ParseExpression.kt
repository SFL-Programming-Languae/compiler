package me.alex_s168.sfl.parser

import me.alex_s168.multiplatform.collection.Node
import me.alex_s168.multiplatform.collection.Stream
import me.alex_s168.sfl.ast.ASTNode
import me.alex_s168.sfl.ast.ASTType
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
                if (expectSep) {
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
            val ident = parseRef(stream, err)!! as Node<ASTNode>
            val next2 = stream.peek()
                ?: return Node<ASTNode>(ASTType(false, false,
                    ident.children.first().value!!.loc tTo
                            ident.children.last().value!!.loc
                ), mutableListOf(ident)) as Node<ASTType>
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
                    } else if (next3.type == TokenType.ANGLE_CLOSE) {
                        level--
                        if (level < 0) {
                            break
                        }
                    } else if (next3.type == TokenType.COMMA && level == 0) {
                        tokens.add(mutableListOf())
                        continue
                    }
                    tokens.last().add(next3)
                }
                if (level != 0) {
                    err.error("Missing closing angle bracket", next2.location)
                }
                val types = mutableListOf(ident)
                tokens.mapTo(types) {
                    parseType(Stream(it.asReversed()), err)!! as Node<ASTNode>
                }
                Node(ASTType(false, false,
                    types.first().value!!.loc tTo
                            types.last().value!!.loc
                ), types) as Node<ASTType>
            }
            Node(ASTType(false, false,
                ident.children.first().value!!.loc tTo
                        ident.children.last().value!!.loc
            ), mutableListOf(ident)) as Node<ASTType>
        }
        else -> {
            err.error("Unexpected token! Expected type", next.location)
            null
        }
    }
}