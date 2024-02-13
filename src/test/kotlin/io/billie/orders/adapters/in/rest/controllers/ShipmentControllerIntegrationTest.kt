package io.billie.orders.adapters.`in`.rest.controllers

import io.billie.orders.adapters.`in`.rest.models.ErrorResponse
import io.billie.orders.adapters.`in`.rest.models.ItemRequest
import io.billie.orders.adapters.`in`.rest.models.ShipmentRequest
import io.billie.orders.adapters.`in`.rest.models.ShipmentResponse
import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.repositories.OrderRepository
import io.billie.orders.app.domain.vo.Item
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertInstanceOf
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpStatus

import java.net.URI
import java.util.UUID


@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ShipmentControllerIntegrationTest {

    @Autowired
    private lateinit var orderRepository: OrderRepository

    @Autowired
    private lateinit var restTemplate: TestRestTemplate

    @Test
    fun shouldCreateANewShipment() {
        val merchantId = UUID.randomUUID()
        val orderItems = listOf(
            Item.create(UUID.fromString("d457b582-7c24-49a7-8a08-10ab2d3d401b")),
            Item.create(UUID.fromString("fa9dbeb6-7ab0-46ba-9f37-e81d1bc645f6")),
            Item.create(UUID.fromString("8afd6b36-3553-4a2d-9dd8-cfc61fe2c247")),
        )

        val order = Order.new(merchantId, orderItems, 1895.3)
        val insertedOrder = orderRepository.createOrder(order)

        val notifyShipmentURI = URI.create("/api/billie/shipments/${insertedOrder.id.value}")
        val shipmentRequest = ShipmentRequest(merchantId, 1895.3, null)
        val response = restTemplate.postForEntity(notifyShipmentURI, shipmentRequest, ShipmentResponse::class.java)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertInstanceOf(ShipmentResponse::class.java, response.body)
        assertInstanceOf(UUID::class.java, response.body?.trackingId)
    }

    @ParameterizedTest
    @MethodSource("failOnValidationDataSource")
    fun shouldFailOnValidation(merchantId: UUID, orderTotal: Double, shipmentTotal: Double, shipmentItems: List<ItemRequest>?) {
        val orderItems = listOf(
            Item.create(UUID.fromString("d457b582-7c24-49a7-8a08-10ab2d3d401b")),
            Item.create(UUID.fromString("fa9dbeb6-7ab0-46ba-9f37-e81d1bc645f6")),
            Item.create(UUID.fromString("8afd6b36-3553-4a2d-9dd8-cfc61fe2c247")),
        )

        val order = Order.new(merchantId, orderItems, orderTotal)
        val insertedOrder = orderRepository.createOrder(order)

        val notifyShipmentURI = URI.create("/api/billie/shipments/${insertedOrder.id.value}")
        val shipmentRequest = ShipmentRequest(merchantId, shipmentTotal, shipmentItems)
        val response = restTemplate.postForEntity(notifyShipmentURI, shipmentRequest, ErrorResponse::class.java)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
    }

    companion object {

        @JvmStatic
        fun failOnValidationDataSource() = listOf<Arguments>(
            Arguments.of(UUID.randomUUID(), 1895.3, 2000, null),
            Arguments.of(UUID.randomUUID(), 1895.3, 100, listOf(Item.create(UUID.randomUUID())))
        )
    }
}
