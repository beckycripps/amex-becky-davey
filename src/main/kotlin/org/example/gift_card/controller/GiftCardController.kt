package org.example.gift_card.controller

import org.example.gift_card.dto.GiftCardDto
import org.example.gift_card.exception.ApiError
import org.example.gift_card.exception.ResourceNotCreatedException
import org.example.gift_card.exception.ResourceNotFoundException
import org.example.gift_card.service.GiftCardService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

/**
 * Controller class for handling Gift Card API endpoints.
 */
@RestController
@RequestMapping("/api/gift_cards")
class GiftCardController(private val giftCardService: GiftCardService) {

    /**
     * POST endpoint to create a new gift card.
     * @param giftCardDto The GiftCardDto object containing gift card details.
     * @return ResponseEntity containing the created GiftCardDto with HTTP status 201 Created.
     */
    @PostMapping
    fun createItem(@RequestBody giftCardDto: GiftCardDto): ResponseEntity<GiftCardDto> {
        val createdGiftCardDto = giftCardService.createGiftCard(giftCardDto)
        return ResponseEntity.status(201).body(createdGiftCardDto)
    }

    /**
     * GET endpoint to retrieve gift cards based on optional parameters.
     * @param value The value of the gift card (optional).
     * @param companyName The company name associated with the gift card (optional).
     * @return ResponseEntity containing a list of GiftCardDto objects matching the search criteria with HTTP status 200 OK.
     */
    @GetMapping
    fun getGiftCards(
        @RequestParam(name = "value", required = false) value: String?,
        @RequestParam(name = "companyName", required = false) companyName: String?
    ): ResponseEntity<List<GiftCardDto?>> {
        val giftCardDtoList = giftCardService.findGiftCards(value, companyName)
        return ResponseEntity.ok(giftCardDtoList)
    }

    /**
     * GET endpoint to retrieve a gift card by its ID.
     * @param id The ID of the gift card to retrieve.
     * @return ResponseEntity containing the GiftCardDto object with HTTP status 200 OK.
     */
    @GetMapping("/{id}")
    fun getGiftCardById(@PathVariable id: String): ResponseEntity<GiftCardDto> {
        val giftCardDto = giftCardService.getGiftCardById(id)
        return ResponseEntity.ok(giftCardDto)
    }

    /**
     * DELETE endpoint to delete a gift card by its ID.
     * @param id The ID of the gift card to delete.
     * @return ResponseEntity with HTTP status 204 No Content upon successful deletion.
     */
    @DeleteMapping("/{id}")
    fun deleteGiftCard(@PathVariable id: String): ResponseEntity<Unit> {
        giftCardService.deleteGiftCard(id)
        return ResponseEntity.noContent().build()
    }

    /**
     * Exception handler for ResourceNotFoundException.
     * @param ex The ResourceNotFoundException instance.
     * @param request The HttpServletRequest object.
     * @return ResponseEntity containing the ApiError object with HTTP status 404 Not Found.
     */
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException, request: HttpServletRequest): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.NOT_FOUND, ex.message ?: "Resource not found")
        return ResponseEntity.status(apiError.status).body(apiError)
    }

    /**
     * Exception handler for ResourceNotCreatedException.
     * @param ex The ResourceNotCreatedException instance.
     * @param request The HttpServletRequest object.
     * @return ResponseEntity containing the ApiError object with HTTP status 500 Internal Server Error.
     */
    @ExceptionHandler(ResourceNotCreatedException::class)
    fun handleResourceNotCreatedException(ex: ResourceNotCreatedException, request: HttpServletRequest): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, ex.message ?: "Server error")
        return ResponseEntity.status(apiError.status).body(apiError)
    }
}
