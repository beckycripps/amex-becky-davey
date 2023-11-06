package org.example.gift_card.exception

import org.springframework.http.HttpStatus

/**
 * Data class representing the structure of an API error response.
 * @property status The HTTP status code of the error response.
 * @property error The error type or reason for the response.
 * @property message A descriptive message explaining the error.
 * @constructor Creates an ApiError instance with the given status code, error type, and message.
 */
data class ApiError(
    val status: Int,
    val error: String,
    val message: String
) {
    /**
     * Secondary constructor that accepts an HttpStatus enum and a message to create an ApiError instance.
     * @param status The HttpStatus enum representing the status code and reason phrase of the error response.
     * @param message A descriptive message explaining the error.
     */
    constructor(status: HttpStatus, message: String) : this(status.value(), status.reasonPhrase, message)
}
