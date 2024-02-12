package io.billie.orders.app.domain.services.validators

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.vo.Item
import org.junit.jupiter.api.BeforeEach

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*

class ShippedItemsInOrderValidatorTest {

    lateinit var subject: ShippedItemsInOrderValidator

    @BeforeEach
    fun setUp() {
        subject = ShippedItemsInOrderValidator()
    }

    @ParameterizedTest
    @MethodSource("validShipmentDataSource")
    fun shouldBeValidShipment(shipment: Shipment) {
        assertTrue(subject.isValid(shipment))
    }

    @ParameterizedTest
    @MethodSource("invalidShipmentDataSource")
    fun shouldNotBeValidShipment(shipment: Shipment) {
        assertFalse(subject.isValid(shipment))
    }


    companion object {

        @JvmStatic
        fun invalidShipmentDataSource(): List<Arguments> {
            val merchantId = UUID.randomUUID()

            return listOf(
                Arguments.of(Shipment.new(
                    100.0,
                    Order.new(merchantId, listOf(), 100.0),
                    listOf(Item(UUID.randomUUID()))
                )),
                Arguments.of(Shipment.new(
                    100.0,
                    Order.new(merchantId, listOf(Item.create(UUID.randomUUID())), 100.0),
                    listOf(Item(UUID.randomUUID()))
                ))
            )
        }

        @JvmStatic
        fun validShipmentDataSource(): List<Arguments> {
            val item1Id = UUID.randomUUID()
            val item2Id = UUID.randomUUID()

            val merchantId = UUID.randomUUID()
            val orderItems = listOf(
                Item.create(item1Id),
                Item.create(item2Id)
            )

            return listOf(
                Arguments.of(
                    Shipment.new(
                        100.0,
                        Order.new(merchantId, listOf(), 100.0),
                        listOf()
                    )
                ),
                Arguments.of(
                    Shipment.new(
                        100.0,
                        Order.new(merchantId, listOf(), 100.0),
                        null
                    )
                ),
                Arguments.of(
                    Shipment.new(
                        100.0,
                        Order.new(merchantId, orderItems, 100.0),
                        listOf(Item(item2Id), Item(item1Id))
                    )
                ),
                Arguments.of(
                    Shipment.new(
                        100.0,
                        Order.new(merchantId, orderItems, 100.0),
                        listOf(Item(item1Id))
                    )
                )
            )
        }
    }
}