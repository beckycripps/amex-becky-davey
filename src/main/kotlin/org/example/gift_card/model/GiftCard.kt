package org.example.gift_card.model

import org.example.gift_card.dto.GiftCardDto
import java.math.BigDecimal
import java.util.*
import javax.persistence.*

/**
 * GiftCard entity representing the gift card details in the database.
 */
@Entity
@Table(name = "gift_card")
class GiftCard(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "company_name")
    val companyName: String,

    @Column(name = "card_value")
    val cardValue: BigDecimal,

    val currency: String,

    @Column(name = "points_cost")
    val pointsCost: Int
) {
    // No-arg constructor for JPA
    constructor() : this(UUID.randomUUID(), "", BigDecimal.ZERO, "", 0)
}

/**
 * Extension function to convert GiftCard entity to GiftCardDto.
 * @return GiftCardDto representation of the GiftCard entity.
 */
fun GiftCard?.toDto(): GiftCardDto? {
    return this?.let {
        GiftCardDto(
            id = it.id.toString(),
            company_name = it.companyName,
            value = it.cardValue.toString(),
            currency = it.currency,
            points_cost = it.pointsCost.toString()
        )
    }
}

/**
 * Extension function to convert GiftCardDto to GiftCard entity.
 * @return GiftCard representation of the GiftCardDto.
 */
fun GiftCardDto.toEntity(): GiftCard {
    return GiftCard(
        companyName = this.company_name,
        cardValue = BigDecimal(this.value),
        currency = this.currency,
        pointsCost = this.points_cost.toInt()
    )
}
