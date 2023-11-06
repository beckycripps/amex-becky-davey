package org.example.gift_card.service

import org.example.gift_card.dto.GiftCardDto
import org.example.gift_card.exception.ResourceNotCreatedException
import org.example.gift_card.exception.ResourceNotFoundException
import org.example.gift_card.model.toDto
import org.example.gift_card.model.toEntity
import org.example.gift_card.repository.GiftCardRepository
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

/**
 * Service class to handle gift card operations.
 */
@Service
class GiftCardService(private val giftCardRepository: GiftCardRepository) {

    /**
     * Retrieves a gift card by its ID from the repository.
     * @param id The ID of the gift card to retrieve.
     * @return The GiftCardDto if found, else throws ResourceNotFoundException.
     */
    fun getGiftCardById(id: String): GiftCardDto? {
        val giftCard = giftCardRepository.findById(UUID.fromString(id))
            .orElseThrow { ResourceNotFoundException("Gift card with ID: $id not found") }
        return giftCard.toDto()
    }

    /**
     * Creates a new gift card and saves it to the repository.
     * @param giftCardDto The GiftCardDto object containing gift card details.
     * @return The created GiftCardDto, or throws ResourceNotCreatedException if creation fails.
     */
    fun createGiftCard(giftCardDto: GiftCardDto): GiftCardDto? {
        val giftCard = giftCardDto.toEntity()
        return try {
            val savedGiftCard = giftCardRepository.save(giftCard)
            savedGiftCard.toDto()
        } catch (e: Exception) {
            throw ResourceNotCreatedException("Failed to create gift card")
        }
    }

    /**
     * Finds gift cards based on optional value and company name parameters.
     * @param valueString The value of the gift card (optional).
     * @param companyName The company name associated with the gift card (optional).
     * @return List of GiftCardDto objects matching the search criteria, or throws ResourceNotFoundException if no cards are found.
     */
    fun findGiftCards(valueString: String?, companyName: String?): List<GiftCardDto?> {
        val value: BigDecimal? = valueString?.let { BigDecimal(it) }
        val giftCards = giftCardRepository.findGiftCardsByValueAndCompanyName(value, companyName)
        if (giftCards.isEmpty()) {
            throw ResourceNotFoundException("No gift cards found")
        }
        return giftCards.map { it.toDto() }
    }

    /**
     * Deletes a gift card by its ID from the repository.
     * @param id The ID of the gift card to delete.
     * @throws ResourceNotFoundException if the gift card with the given ID is not found.
     */
    fun deleteGiftCard(id: String) {
        if (giftCardRepository.existsById(UUID.fromString(id))) {
            giftCardRepository.deleteById(UUID.fromString(id))
        } else {
            throw ResourceNotFoundException("Gift card with ID: $id not found")
        }
    }
}
