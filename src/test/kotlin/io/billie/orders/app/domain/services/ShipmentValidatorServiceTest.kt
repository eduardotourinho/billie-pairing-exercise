package io.billie.orders.app.domain.services

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.exceptions.InvalidShipmentTotalException
import io.billie.orders.app.domain.exceptions.ShippedItemsNotInOrderException
import io.billie.orders.app.domain.repositories.ShipmentRepository
import io.billie.orders.app.domain.services.validators.ShipmentTotalValidator
import io.billie.orders.app.domain.services.validators.ShippedItemsInOrderValidator
import io.billie.orders.app.domain.vo.Item
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class ShipmentValidatorServiceTest {

    @Mock
    lateinit var shipmentTotalValidator: ShipmentTotalValidator

    @Mock
    lateinit var shippedItemsInOrderValidator: ShippedItemsInOrderValidator

    @Mock
    lateinit var shipmentRepository: ShipmentRepository

    @InjectMocks
    lateinit var subject: ShipmentValidatorService


    @Test
    fun shouldValidateShipmentWithoutErrors() {
        val order = Order.new(UUID.randomUUID(), 200.0)
        val shipment = Shipment.new(100.0, order, null)
        val prevShipments = listOf<Shipment>()

        `when`(shipmentRepository.findByOrder(order.id, order.merchantId))
            .thenReturn(prevShipments)

        `when`(shipmentTotalValidator.isValid(shipment, prevShipments))
            .thenReturn(true)

        `when`(shippedItemsInOrderValidator.isValid(shipment))
            .thenReturn(true)

        assertDoesNotThrow {
            assertTrue(subject.validate(shipment))
        }
    }

    @Test
    fun shouldThrowShippedItemsNotInOrder() {
        val orderItems = listOf(Item.create(UUID.randomUUID()))
        val order = Order.new(UUID.randomUUID(), orderItems, 200.0)

        val shippedItems = listOf(Item.create(UUID.randomUUID()))
        val shipment = Shipment.new(100.0, order, shippedItems)

        `when`(shippedItemsInOrderValidator.isValid(shipment))
            .thenReturn(false)

        assertThrows(ShippedItemsNotInOrderException::class.java) {
            subject.validate(shipment)
        }
    }

    @Test
    fun shouldThrowInvalidShipmentTotal() {
        val orderItems = listOf(Item.create(UUID.randomUUID()))
        val order = Order.new(UUID.randomUUID(), orderItems, 200.0)

        val shippedItems = listOf(Item.create(UUID.randomUUID()))
        val shipment = Shipment.new(200.0, order, shippedItems)

        val prevShipments = listOf<Shipment>()

        `when`(shippedItemsInOrderValidator.isValid(shipment))
            .thenReturn(true)

        `when`(shipmentRepository.findByOrder(order.id, order.merchantId))
            .thenReturn(prevShipments)

        `when`(shipmentTotalValidator.isValid(shipment, prevShipments))
            .thenReturn(false)

        assertThrows(InvalidShipmentTotalException::class.java) {
            subject.validate(shipment)
        }
    }
}
