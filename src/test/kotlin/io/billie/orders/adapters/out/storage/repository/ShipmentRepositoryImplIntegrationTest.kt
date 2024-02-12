package io.billie.orders.adapters.out.storage.repository

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.repositories.OrderRepository
import io.billie.orders.app.domain.vo.Item
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

import java.util.*


@SpringBootTest
class ShipmentRepositoryImplIntegrationTest {

    @Autowired
    lateinit var orderRepository: OrderRepository

    @Autowired
    lateinit var subject: ShipmentRepositoryImpl

    @Test
    fun shouldInsertAndFindShipment() {
        val merchantId = UUID.randomUUID()
        val orderItems = listOf(
            Item.create(UUID.fromString("d457b582-7c24-49a7-8a08-10ab2d3d401b")),
            Item.create(UUID.fromString("fa9dbeb6-7ab0-46ba-9f37-e81d1bc645f6")),
            Item.create(UUID.fromString("8afd6b36-3553-4a2d-9dd8-cfc61fe2c247")),
        )
        val order = Order.new(merchantId, orderItems, 1895.3)
        val insertedOrder = orderRepository.createOrder(order)

        assertEquals(order.id, insertedOrder.id)

        val actualShipment = subject.insertShipment(Shipment.new(1000.0, insertedOrder, listOf()))
        val expectedShipments = subject.findByOrder(order.id, merchantId)

        assertEquals(1, expectedShipments.size)

        assertEquals(expectedShipments[0].id, actualShipment.id)
        assertEquals(expectedShipments[0].order!!.id, actualShipment.order!!.id)
        assertEquals(expectedShipments[0].total.toDouble(), actualShipment.total.toDouble())
    }
}