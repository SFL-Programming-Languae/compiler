package me.alex_s168.sfl.lexer

import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.error.error
import me.alex_s168.sfl.location.SourceLocation
import me.alex_s168.sfl.location.TokenLocation

private val simpleTokens = listOf(
    "(" to TokenType.PAREN_OPEN,
    ")" to TokenType.PAREN_CLOSE,
    "[" to TokenType.BRACKET_OPEN,
    "]" to TokenType.BRACKET_CLOSE,
    "{" to TokenType.BRACE_OPEN,
    "}" to TokenType.BRACE_CLOSE,
    "<" to TokenType.ANGLE_OPEN,
    ">" to TokenType.ANGLE_CLOSE,
    "," to TokenType.COMMA,
    "." to TokenType.DOT,
    ":" to TokenType.COLON,
    "::" to TokenType.NAMESPACE,
    ";" to TokenType.SEMICOLON,
    "!" to TokenType.EXCLAMATION,
    "~" to TokenType.WAVE,

    "=" to TokenType.ASSIGN,
    "+=" to TokenType.INC_ASSIGN,
    "-=" to TokenType.DEC_ASSIGN,
    "*=" to TokenType.MUL_ASSIGN,
    "/=" to TokenType.DIV_ASSIGN,
    "%=" to TokenType.MOD_ASSIGN,
    "++" to TokenType.INC,
    "--" to TokenType.DEC,

    "==" to TokenType.EQ,
    "!=" to TokenType.NEQ,
    ">=" to TokenType.GTE,
    "<=" to TokenType.LTE,

    "+" to TokenType.ADD,
    "-" to TokenType.SUB,
    "*" to TokenType.MUL,
    "/" to TokenType.DIV,
    "%" to TokenType.MOD,
    "^" to TokenType.EXP,

    "&&" to TokenType.BOOL_AND,
    "||" to TokenType.BOOL_OR,


    "if" to TokenType.KW_IF,
    "else" to TokenType.KW_ELSE,
    "while" to TokenType.KW_WHILE,
    "for" to TokenType.KW_FOR,
    "in" to TokenType.KW_IN,
    "repeat" to TokenType.KW_REPEAT,
    "break" to TokenType.KW_BREAK,
    "continue" to TokenType.KW_CONTINUE,
    "return" to TokenType.KW_RETURN,
    "or" to TokenType.KW_OR,
    "new" to TokenType.KW_NEW,
    "as" to TokenType.KW_AS,
    "mut" to TokenType.KW_MUT,
    "fun" to TokenType.KW_FUN,
    "class" to TokenType.KW_CLASS,
    "interface" to TokenType.KW_INTERFACE,
    "enum" to TokenType.KW_ENUM,
    "do" to TokenType.KW_DO,
    "catch" to TokenType.KW_CATCH,
    "external" to TokenType.KW_EXTERNAL,
    "private" to TokenType.KW_PRIVATE,
    "protected" to TokenType.KW_PROTECTED,
    "internal" to TokenType.KW_INTERNAL,
    "throws" to TokenType.KW_THROWS,
    "throw" to TokenType.KW_THROW,
    "handle" to TokenType.KW_HANDLE,
    "errors" to TokenType.KW_ERRORS,
    "by" to TokenType.KW_BY,

    "true" to TokenType.LIT_BOOL_TRUE,
    "false" to TokenType.LIT_BOOL_FALSE,
    "none" to TokenType.LIT_NONE,

    /*
    IDENTIFIER, // a-z, A-Z, 0-9, _, -
    LIT_INT, // [0-9]+
    LIT_FLOAT, // [0-9]+.[0-9]+
    LIT_STRING, // ".*"
    LIT_CHAR, // '.'
     */
).sortedBy {
    it.first.length
}.reversed()

private fun getLineAndColumn(source: String, offset: Int): Pair<Int, Int> {
    var line = 1
    var column = 1
    for (i in 0..<offset) {
        if (source[i] == '\n') {
            line++
            column = 1
        } else {
            column++
        }
    }
    return line to column
}

fun lex(
    sourceLocation: SourceLocation,
    errors: ErrorContext,
    offset: Int = 0
): List<Token> {
    val source = sourceLocation.source
    val tokens = mutableListOf<Token>()
    var index = offset
    while (index < source.length) {
        val char = source[index]
        if (char.isWhitespace()) {
            index++
            continue
        }
        // TODO: slow
        val (line, column) = getLineAndColumn(source, index)
        val token = simpleTokens.firstOrNull {
            source.startsWith(it.first, index)
        }
        if (token != null) {
            val loc = TokenLocation(
                sourceLocation,
                line,
                column,
                token.first.length
            )
            tokens += Token(token.second, loc)
            index += token.first.length
            continue
        }
        if (char.isDigit()) {
            val start = index
            while (index < source.length && source[index].isDigit()) {
                index++
            }
            if (index < source.length && source[index] == '.') {
                index++
                while (index < source.length && source[index].isDigit()) {
                    index++
                }
                tokens += Token(
                    TokenType.LIT_FLOAT,
                    TokenLocation(
                        sourceLocation,
                        line,
                        column,
                        index - start
                    ),
                    source.substring(start, index)
                )
            } else {
                tokens += Token(
                    TokenType.LIT_INT,
                    TokenLocation(
                        sourceLocation,
                        line,
                        column,
                    index - start
                    ),
                    source.substring(start, index)
                )
            }
            continue
        }
        if (char == '"') {
            val start = index
            index++
            while (index < source.length && source[index] != '"') {
                if (source[index] == '\\') {
                    index++
                }
                index++
            }
            if (index < source.length) {
                index++
            }
            tokens += Token(
                TokenType.LIT_STRING,
                TokenLocation(
                    sourceLocation,
                    line,
                    column,
                    index - start
                ),
                source.substring(start, index)
            )
            continue
        }
        if (char == '\'') {
            val start = index
            index++
            while (index < source.length && source[index] != '\'') {
                if (source[index] == '\\') {
                    index++
                }
                index++
            }
            if (index < source.length) {
                index++
            }
            tokens += Token(
                TokenType.LIT_CHAR,
                TokenLocation(
                    sourceLocation,
                    line,
                    column,
                    index - start
                ),
                source.substring(start, index)
            )
            continue
        }
        if (char.isLetter()) {
            val start = index
            while (index < source.length && source[index].isPartOfIdentifier()) {
                index++
            }
            tokens += Token(
                TokenType.IDENTIFIER,
                TokenLocation(
                    sourceLocation,
                    line,
                    column,
                    index - start
                ),
                source.substring(start, index)
            )
            continue
        }
        errors.error(
            "Unexpected symbol",
            TokenLocation(
                sourceLocation,
                line,
                column,
                1
            )
        )
        break
    }
    return tokens
}

private fun Char.isPartOfIdentifier(): Boolean {
    return isLetterOrDigit() || this == '_' || this == '-'
}