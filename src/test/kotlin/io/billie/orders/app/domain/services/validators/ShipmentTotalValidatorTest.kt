package io.billie.orders.app.domain.services.validators

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*
import kotlin.collections.List

class ShipmentTotalValidatorTest {

    private lateinit var subject: ShipmentTotalValidator

    @BeforeEach
    fun setUp() {
        subject = ShipmentTotalValidator()
    }

    @ParameterizedTest
    @MethodSource("validShipmentDataSource")
    fun shouldBeValidWithShipmentTotalLowerThanTotalOrder(shipment: Shipment, previousShipments: List<Shipment>) {
        val actualIsValid = subject.isValid(shipment, previousShipments)

        assertTrue(actualIsValid)
    }

    @ParameterizedTest
    @MethodSource("invalidShipmentDataSource")
    fun shouldFailValidationWithShipmentValueBiggerThanTotalOrder(shipment: Shipment, previousShipments: List<Shipment>) {
        val actualIsValid = subject.isValid(shipment, previousShipments)

        assertFalse(actualIsValid)
    }

    companion object {

        @JvmStatic
        fun validShipmentDataSource() = listOf<Arguments>(
            Arguments.of(
                Shipment.new(100.0, Order.new(UUID.randomUUID(),100.0), null),
                listOf<Shipment>()
            ),
            Arguments.of(
                Shipment.new(1.0, Order.new(UUID.randomUUID(),100.0), null),
                listOf<Shipment>()
            ),
            Arguments.of(
                Shipment.new(10.0, Order.new(UUID.randomUUID(),100.0), null),
                listOf(
                    Shipment.new(34.2),
                    Shipment.new(13.5)
                )
            ),
            Arguments.of(
                Shipment.new(10.0, Order.new(UUID.randomUUID(),100.0), null),
                listOf(
                    Shipment.new(60.0),
                    Shipment.new(30.0)
                )
            )
        )

        @JvmStatic
        fun invalidShipmentDataSource() = listOf(
            Arguments.of(
                Shipment.new(0.0, Order.new(UUID.randomUUID(),100.0), null),
                listOf<Shipment>()
            ),
            Arguments.of(
                Shipment.new(100.01, Order.new(UUID.randomUUID(),100.0), null),
                listOf<Shipment>()
            ),
            Arguments.of(
                Shipment.new(10.0, Order.new(UUID.randomUUID(),100.0), null),
                listOf(
                    Shipment.new(60.0),
                    Shipment.new(40.0)
                )
            )
        )
    }
}
