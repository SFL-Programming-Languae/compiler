package me.alex_s168.sfl.location

fun TokenLocation.to(other: TokenLocation) = TokenLocation(
    source = source,
    line = line,
    column = column,
    length = other.column + other.length - column
)