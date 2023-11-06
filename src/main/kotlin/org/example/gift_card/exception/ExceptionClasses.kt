package org.example.gift_card.exception

/**
 * Custom exception class indicating that a requested resource was not found.
 * @param message A descriptive message explaining the specific error condition.
 */
class ResourceNotFoundException(message: String) : RuntimeException(message)

/**
 * Custom exception class indicating that a resource could not be created.
 * @param message A descriptive message explaining the specific error condition.
 */
class ResourceNotCreatedException(message: String) : RuntimeException(message)
