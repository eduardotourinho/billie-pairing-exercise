package io.billie.orders.adapters.out.storage.services

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.exceptions.ShipmentNotificationException
import io.billie.orders.app.domain.repositories.ShipmentRepository
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class ShipmentNotifierServiceTest {

    @Mock
    lateinit var shipmentRepository: ShipmentRepository

    @InjectMocks
    lateinit var subject: ShipmentNotifierService

    @Test
    fun shouldInsertShipmentAndReturnNewShipmentId() {
        val shipment = Shipment.new(100.0, Order.new(UUID.randomUUID(), 100.0), listOf())

        Mockito.`when`(shipmentRepository.insertShipment(shipment))
            .thenReturn(shipment)

        val actualId = subject.notifyShipment(shipment)

        assertInstanceOf(Shipment.ShipmentId::class.java, actualId)
        assertEquals(shipment.id, actualId)
    }

    @Test
    fun shouldThrowShipmentNotificationException() {
        val shipment = Shipment.new(100.0, Order.new(UUID.randomUUID(), 100.0), listOf())

        Mockito.`when`(shipmentRepository.insertShipment(shipment))
            .thenThrow(RuntimeException("new row for relation \"shipments\" violates check constraint \"shipments_total_check\""))

        assertThrows(ShipmentNotificationException::class.java) {
            subject.notifyShipment(shipment)
        }
    }
}
