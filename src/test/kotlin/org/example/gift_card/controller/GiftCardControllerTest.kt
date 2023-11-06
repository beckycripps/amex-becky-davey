package org.example.gift_card.controller

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

class GiftCardControllerTest {


    @SpringBootTest
    @AutoConfigureMockMvc
    class YourControllerClassTest {

        @Autowired
        lateinit var mockMvc: MockMvc

        @Test
        fun testControllerAPI() {
            val requestJson =
                "{\"company_name\":\"Company Name\",\"value\":\"200.00\",\"currency\":\"GBP\", \"points_cost\":\"1000\"}"

            // Perform the POST request with the JSON content and verify the response status
            performPostRequest("/api/gift_cards", requestJson)
                .andExpectStatusCreated()
        }


        @Test
        fun getGiftCardById() {
            val requestJson =
                "{\"company_name\":\"Company Name\",\"value\":\"200.00\",\"currency\":\"GBP\", \"points_cost\":\"1000\"}"

            // Perform the POST request with the JSON content and verify the response status
            val response = performPostRequest("/api/gift_cards", requestJson)
                .andExpectStatusCreated()

            val id = extractIdFromResponse(response)
            performGetByIdRequest("/api/gift_cards/{id}", id)
                .andExpectStatusOk()
        }

        @Test
        fun getGiftCardByParamsTest() {
            val requestJson =
                "{\"company_name\":\"Company Fred\",\"value\":\"300.00\",\"currency\":\"GBP\", \"points_cost\":\"1000\"}"

            // Perform the POST request with the JSON content and verify the response status
            val response = performPostRequest("/api/gift_cards", requestJson)
                .andExpectStatusCreated()

            performGetByParamsRequest("/api/gift_cards/", "300", "Company Sid")
                .andExpectStatusNotFound()

            performGetByParamsRequest("/api/gift_cards/", "300", "Company Fred")
                .andExpectStatusOk()

            performGetByParamsRequest("/api/gift_cards/", null, "Company Fred")
                .andExpectStatusOk()

            performGetByParamsRequest("/api/gift_cards/", "300", null)
                .andExpectStatusOk()

            performGetByParamsRequest("/api/gift_cards/", "5000", null)
                .andExpectStatusNotFound()

            performGetByParamsRequest("/api/gift_cards/", null, null)
                .andExpectStatusOk()
        }


        @Test
        fun deleteGiftCard() {
            val requestJson =
                "{\"company_name\":\"Company Name\",\"value\":\"200.00\",\"currency\":\"GBP\", \"points_cost\":\"1000\"}"

            // Perform the POST request with the JSON content and verify the response status
            val response = performPostRequest("/api/gift_cards", requestJson)
                .andExpectStatusCreated()

            val id = extractIdFromResponse(response)
            performDeleteRequest("/api/gift_cards/{id}", id)
                .andExpectStatusDeleted()
        }

        private fun performPostRequest(url: String, content: String): ResultActions {
            return mockMvc.perform(
                post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content)
            )
                .andDo { println() }
        }

        private fun performGetByIdRequest(url: String, id: String): ResultActions {
            return mockMvc.perform(
                get(url, id)
            )
                .andDo { println() }
        }


        private fun performGetByParamsRequest(url: String, value: String?, companyName: String?): ResultActions {
            val params: MultiValueMap<String, String> = LinkedMultiValueMap()
            value?.let { params.add("value", it) }
            companyName?.let { params.add("companyName", it) }

            return mockMvc.perform(
                get(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .queryParams(params)
            )
                .andDo { println() }
        }

        private fun performDeleteRequest(url: String, id: String): ResultActions {
            return mockMvc.perform(
                delete(url, id)
            )
                .andDo { println() }

        }


        fun extractIdFromResponse(response: ResultActions): String {
            val result: MvcResult = response.andReturn()
            val contentAsString = result.response.contentAsString
            val objectMapper = ObjectMapper()
            val responseObject = objectMapper.readTree(contentAsString)
            return responseObject["id"].asText()
        }

        private fun ResultActions.andExpectStatusOk(): ResultActions {
            return this.andExpect(status().isOk)
        }

        private fun ResultActions.andExpectStatusCreated(): ResultActions {
            return this.andExpect(status().isCreated)
        }


        private fun ResultActions.andExpectStatusDeleted(): ResultActions {
            return this.andExpect(status().isNoContent)
        }

        private fun ResultActions.andExpectStatusNotFound(): ResultActions {
            return this.andExpect(status().isNotFound)
        }

    }
}
