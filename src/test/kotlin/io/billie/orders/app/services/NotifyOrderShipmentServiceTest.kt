package io.billie.orders.app.services

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.repositories.OrderRepository
import io.billie.orders.app.domain.services.ShipmentValidatorService
import io.billie.orders.app.domain.vo.OrderStatus
import io.billie.orders.app.ports.`in`.NotifyOrderShipmentUseCase
import io.billie.orders.app.ports.out.ShipmentNotifierPort
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import java.math.BigDecimal
import java.util.*


@ExtendWith(MockitoExtension::class)
class NotifyOrderShipmentServiceTest {

    @Mock
    lateinit var shipmentValidator: ShipmentValidatorService

    @Mock
    lateinit var shipmentNotifierPort: ShipmentNotifierPort

    @Mock
    lateinit var orderRepository: OrderRepository

    @InjectMocks
    lateinit var subject: NotifyOrderShipmentService

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    @Test
    fun shouldNotifyTheShipmentAndReturnTheNewShipmentId() {
        val merchantId = UUID.randomUUID()
        val orderUuid = UUID.randomUUID()
        val order = Order(
            Order.OrderId(orderUuid),
            merchantId, listOf(),
            BigDecimal.valueOf(1000.0),
            OrderStatus.NOT_SHIPPED
        )
        val expectedShipmentId = Shipment.ShipmentId(UUID.randomUUID())

        `when`(orderRepository.findByMerchantAndId(merchantId, order.id))
            .thenReturn(order)

        `when`(shipmentValidator.validate(any(Shipment::class.java)))
            .thenReturn(true)

        `when`(shipmentNotifierPort.notifyShipment(any(Shipment::class.java)))
            .thenReturn(expectedShipmentId)

        val command = NotifyOrderShipmentUseCase.ShipmentNotificationCommand(
            merchantId,
            order.id,
            100.0,
            null,
        )
        val actualShipmentId = subject.notifyShipment(command)

        verify(shipmentNotifierPort, times(1))
            .notifyShipment(any(Shipment::class.java))

        assertInstanceOf(Shipment.ShipmentId::class.java, actualShipmentId)
        assertEquals(expectedShipmentId, actualShipmentId)
    }
}