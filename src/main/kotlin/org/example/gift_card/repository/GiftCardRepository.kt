package org.example.gift_card.repository

import org.example.gift_card.model.GiftCard
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.math.BigDecimal
import java.util.*

/**
 * Repository interface for managing GiftCard entities in the database.
 */
interface GiftCardRepository : JpaRepository<GiftCard, UUID> {

    /**
     * Custom query to find GiftCards based on their card value and company name.
     * @param value The card value to search for (optional).
     * @param companyName The company name to search for (optional).
     * @return A list of GiftCard entities matching the specified criteria.
     */
    @Query("SELECT g FROM GiftCard g WHERE (:value IS NULL OR g.cardValue = :value) AND (:companyName IS NULL OR g.companyName = :companyName)")
    fun findGiftCardsByValueAndCompanyName(value: BigDecimal?, companyName: String?): List<GiftCard>
}
