package me.alex_s168.sfl.location

data class TokenLocation(
    val source: SourceLocation,
    val line: Int,
    val column: Int,
    val length: Int,
) {
    override fun toString() = "${source.fileAbsPath}:$line:$column"
}