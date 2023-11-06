package org.example.gift_card.dto

data class GiftCardDto (
    val id: String?,
    val company_name: String,
    val value: String,
    val currency: String,
    val points_cost: String
)
