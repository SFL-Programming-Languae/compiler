import me.alex_s168.sfl.error.ErrorContext
import me.alex_s168.sfl.error.hasErrors
import me.alex_s168.sfl.lexer.lex
import me.alex_s168.sfl.location.SourceLocation
import kotlin.test.Test
import kotlin.test.assertEquals

@Test
fun testLexer0() {
    val source = """
        fun main() {
            println("Hello, World!");
        }
    """.trimIndent()
    val errors = ErrorContext()
    val loc = SourceLocation(
        source = source,
        fileAbsPath = "testLexer0"
    )
    val tokens = lex(loc, errors)
    tokens.setDone()
    assertEquals(false, errors.hasErrors())
    println("Tokens:")
    for (token in tokens.reversed()) {
        println(token)
    }
    println("===")
}