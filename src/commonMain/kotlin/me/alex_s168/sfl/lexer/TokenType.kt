package me.alex_s168.sfl.lexer

enum class TokenType {
    PAREN_OPEN, // '('
    PAREN_CLOSE, // ')'
    BRACE_OPEN, // '{'
    BRACE_CLOSE, // '}'
    BRACKET_OPEN, // '['
    BRACKET_CLOSE, // ']'
    ANGLE_OPEN, // '<' (also used for less than)
    ANGLE_CLOSE, // '>' (also used for greater than)
    COMMA, // ','
    DOT, // '.'
    COLON, // ':'
    NAMESPACE, // '::'
    SEMICOLON, // ';'
    EXCLAMATION, // '!' (also used for boolean not)
    WAVE, // '~'
    UNDERSCORE, // '_'

    ASSIGN, // '='
    INC_ASSIGN, // '+='
    DEC_ASSIGN, // '-='
    MUL_ASSIGN, // '*='
    DIV_ASSIGN, // '/='
    MOD_ASSIGN, // '%='
    INC, // '++'
    DEC, // '--'

    EQ, // '=='
    NEQ, // '!='
    GTE, // '>='
    LTE, // '<='

    ADD, // '+'
    SUB, // '-'
    MUL, // '*'
    DIV, // '/'
    MOD, // '%'
    EXP, // '^'

    BOOL_AND, // '&&'
    BOOL_OR, // '||'


    KW_IF, // 'if'
    KW_ELSE, // 'else'
    KW_WHILE, // 'while'
    KW_FOR, // 'for'
    KW_IN, // 'in'
    KW_REPEAT, // 'repeat'
    KW_BREAK, // 'break'
    KW_CONTINUE, // 'continue'
    KW_RETURN, // 'return'
    KW_OR, // 'or'
    KW_NEW, // 'new'
    KW_AS, // 'as'
    KW_MUT, // 'mut'
    KW_FUN, // 'fun'
    KW_CLASS, // 'class'
    KW_INTERFACE, // 'interface'
    KW_ENUM, // 'enum'
    KW_DO, // 'do'
    KW_CATCH, // 'catch'
    KW_EXTERNAL, // 'external'
    KW_PRIVATE, // 'private'
    KW_PROTECTED, // 'protected'
    KW_INTERNAL, // 'internal'
    KW_THROWS, // 'throws'
    KW_THROW, // 'throw'
    KW_HANDLE, // 'handle'
    KW_ERRORS, // 'errors'
    KW_BY, // 'by'

    IDENTIFIER, // a-z, A-Z, 0-9, _, -
    LIT_INT, // [0-9]+
    LIT_FLOAT, // [0-9]+.[0-9]+
    LIT_STRING, // ".*"
    LIT_CHAR, // '.'
    LIT_BOOL_TRUE, // 'true'
    LIT_BOOL_FALSE, // 'false'
    LIT_NONE, // 'none'
}