import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.error.hasErrors
import me.alex_s168.sfl.lexer.lex
import me.alex_s168.sfl.location.SourceLocation
import me.alex_s168.sfl.parser.parseRef
import me.alex_s168.sfl.parser.parseType
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@Test
fun testParser0() {
    val source = """
        std::err::Error<
    """.trimIndent()
    val errors = ErrorContext()
    val loc = SourceLocation(
        source = source,
        fileAbsPath = "testParser0"
    )
    val tokens = lex(loc, errors)
    tokens.setDone()
    assertEquals(false, errors.hasErrors(), errors.toString())
    val ast = parseRef(tokens, errors)
    assertEquals(false, errors.hasErrors(), errors.toString())
    assertEquals(tokens.size, 1, "Expected all except one token to be consumed. Left: ${tokens.joinToString { it.toString() }}")
    assertNotNull(ast, "Expected AST to be non-null")
    assertEquals(ast.value!!.parts.size, 5, "Expected 5 parts in ASTRef")
    println("AST:")
    println(ast)
    println("===")
}

@Test
fun testParser1() {
    val source = """
        mut std::collections::Vec<Yo>
    """.trimIndent()
    val errors = ErrorContext()
    val loc = SourceLocation(
        source = source,
        fileAbsPath = "testParser0"
    )
    val tokens = lex(loc, errors)
    tokens.setDone()
    println("Tokens:")
    for (token in tokens.reversed()) {
        println(token)
    }
    println("===")
    assertEquals(false, errors.hasErrors(), errors.toString())
    val ast = parseType(tokens, errors)
    assertEquals(false, errors.hasErrors(), errors.toString())
    println("AST:")
    println(ast)
    println("===")
    assertTrue(tokens.isEmpty(), "Expected all tokens to be consumed. Left: ${tokens.joinToString { it.toString() }}")
}