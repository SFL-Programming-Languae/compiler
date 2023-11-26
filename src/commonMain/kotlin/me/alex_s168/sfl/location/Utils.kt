package me.alex_s168.sfl.location

infix fun TokenLocation.tTo(other: TokenLocation) = TokenLocation(
    source = source,
    line = line,
    column = column,
    length = other.column + other.length - column
)