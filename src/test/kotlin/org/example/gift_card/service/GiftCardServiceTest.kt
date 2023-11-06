package org.example.gift_card.service

import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.example.gift_card.dto.GiftCardDto
import org.example.gift_card.exception.ResourceNotFoundException
import org.example.gift_card.model.GiftCard
import org.example.gift_card.model.toDto
import org.example.gift_card.repository.GiftCardRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any

import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.util.*

@SpringBootTest
class GiftCardServiceTest {

    @InjectMocks
    private lateinit var giftCardService: GiftCardService

    @Mock
    private lateinit var giftCardRepository: GiftCardRepository

    private val id = UUID.randomUUID().toString()

    @Test
    fun `should return GiftCardDto when given valid ID`() {
        // Arrange
        val expectedGiftCard = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name",
            cardValue = BigDecimal("100.00"),
            currency = "USD",
            pointsCost = 10
        )
        val expectedDto = expectedGiftCard.toDto()

        `when`(giftCardRepository.findById(UUID.fromString(expectedGiftCard.id.toString()))).thenReturn(Optional.of(expectedGiftCard))

        // Act
        val result = giftCardService.getGiftCardById(expectedGiftCard.id.toString())

        // Assert
        assertThat(result).isEqualTo(expectedDto)
        verify(giftCardRepository).findById(UUID.fromString(expectedGiftCard.id.toString()))
    }

    @Test
    fun `should return null when given invalid ID`() {
        // Arrange
        val id = UUID.randomUUID().toString()
        `when`(giftCardRepository.findById(UUID.fromString(id))).thenReturn(Optional.empty())
        // Assert
        assertThrows<ResourceNotFoundException> {giftCardService.getGiftCardById(id)}
        verify(giftCardRepository).findById(UUID.fromString(id))
    }

    @Test
    fun `should return GiftCardDtoList when given value param`() {
        // Arrange
        val expectedGiftCard1 = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name1",
            cardValue = BigDecimal("100.00"),
            currency = "USD",
            pointsCost = 10
        )
        val expectedGiftCard2 = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name2",
            cardValue = BigDecimal("100.00"),
            currency = "GBP",
            pointsCost = 1000
        )
        val expectedGiftCard3 = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name3",
            cardValue = BigDecimal("100.00"),
            currency = "USD",
            pointsCost = 2000
        )
        val giftCardList = listOf(expectedGiftCard1,expectedGiftCard2,expectedGiftCard3)


        `when`(giftCardRepository.findGiftCardsByValueAndCompanyName(value = BigDecimal("100.00"), companyName = null)).thenReturn(giftCardList)

        // Act
        val resultDto = giftCardService.findGiftCards("100.00", null)

        // Assert
        assertThat(resultDto.size).isEqualTo(3)
        verify(giftCardRepository).findGiftCardsByValueAndCompanyName(any(), any())
    }

    @Test
    fun `should return GiftCardDtoList when given params are null`() {
        // Arrange
        val expectedGiftCard1 = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name1",
            cardValue = BigDecimal("100.00"),
            currency = "USD",
            pointsCost = 10
        )
        val expectedGiftCard2 = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name2",
            cardValue = BigDecimal("100.00"),
            currency = "GBP",
            pointsCost = 1000
        )
        val expectedGiftCard3 = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name3",
            cardValue = BigDecimal("100.00"),
            currency = "USD",
            pointsCost = 2000
        )
        val giftCardList = listOf(expectedGiftCard1,expectedGiftCard2,expectedGiftCard3)


        `when`(giftCardRepository.findGiftCardsByValueAndCompanyName(value = null, companyName = null)).thenReturn(giftCardList)

        // Act
        val resultDto = giftCardService.findGiftCards(null, null)

        // Assert
        assertThat(resultDto.size).isEqualTo(3)
        verify(giftCardRepository).findGiftCardsByValueAndCompanyName(any(), any())
    }

    @Test
    fun `should throw not found when findGiftCards returns null`() {
        `when`(giftCardRepository.findById(UUID.fromString(id))).thenReturn(Optional.empty())
        // Assert
        assertThrows<ResourceNotFoundException> {giftCardService.findGiftCards(any(), any())}
        verify(giftCardRepository).findGiftCardsByValueAndCompanyName(any(), any())
    }


    @Test
    fun `should create and return GiftCardDto`() {
        // Arrange

        val expectedGiftCard = GiftCard(
            id = UUID.randomUUID(),
            companyName = "Company Name",
            cardValue = BigDecimal("100.00"),
            currency = "USD",
            pointsCost = 10
        )

        val giftCardToSaveDto = GiftCardDto(
            id = null,
            company_name = "Company Name",
            value = "100.00",
            currency = "USD",
            points_cost = "10"
        )

        val resultGiftCardDto = GiftCardDto(
            id = expectedGiftCard.id.toString(),
            company_name = "Company Name",
            value = "100.00",
            currency = "USD",
            points_cost = "10"
        )

        `when`(giftCardRepository.save(any(GiftCard::class.java))).thenReturn(expectedGiftCard)
        // Act
        val result = giftCardService.createGiftCard(giftCardToSaveDto)

        // Assert
        assertThat(result).isEqualTo(resultGiftCardDto)
        verify(giftCardRepository).save(any(GiftCard::class.java))
    }

    @Test
    fun `should delete GiftCard by ID`() {
        // Arrange

        `when`(giftCardRepository.existsById(UUID.fromString(id))).thenReturn(true)

        // Act
        giftCardService.deleteGiftCard(id)

        // Assert
        verify(giftCardRepository).existsById(UUID.fromString(id))
        verify(giftCardRepository).deleteById(UUID.fromString(id))
    }

    @Test
    fun `should throw exception when trying to delete non-existing GiftCard`() {
        // Arrange


        `when`(giftCardRepository.existsById(UUID.fromString(id))).thenReturn(false)

        // Act & Assert
        assertThrows<ResourceNotFoundException> { giftCardService.deleteGiftCard(id) }
        verify(giftCardRepository).existsById(UUID.fromString(id))
        verify(giftCardRepository, never()).deleteById(UUID.fromString(id))
    }
}
