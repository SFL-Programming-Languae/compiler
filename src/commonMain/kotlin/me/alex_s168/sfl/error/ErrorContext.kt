package me.alex_s168.sfl.error

import me.alex_s168.sfl.location.TokenLocation

class ErrorContext {
    val errors = mutableListOf<Error>()
}

fun ErrorContext.hasErrors(
    severity: Severity
) = errors.any { it.severity == severity }

fun ErrorContext.hasErrors() =
    errors.isNotEmpty()

fun ErrorContext.error(error: Error) {
    errors += error
}

fun ErrorContext.error(
    message: String,
    location: TokenLocation,
    severity: Severity = Severity.ERROR
) {
    error(Error(message, location, severity))
}