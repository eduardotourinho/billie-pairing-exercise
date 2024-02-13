package io.billie.orders.app.domain.entities

import io.billie.orders.app.domain.vo.Item
import io.billie.orders.app.domain.vo.OrderStatus
import java.math.BigDecimal
import java.util.UUID

data class Order(
    val id: OrderId,
    val merchantId: UUID,
    val items: List<Item> = listOf(),
    val total: BigDecimal = BigDecimal.ZERO,
    val status: OrderStatus
) {

    data class OrderId(override val value: UUID) : Identity {

    }

    companion object Factory {

        fun new(merchantId: UUID, total: Double): Order {
            return Order(
                OrderId(UUID.randomUUID()),
                merchantId,
                listOf(),
                total.toBigDecimal(),
                OrderStatus.NOT_SHIPPED
            )
        }

        fun new(merchantId: UUID, items: List<Item>, total: Double): Order {
            return Order(OrderId(UUID.randomUUID()), merchantId, items, total.toBigDecimal(), OrderStatus.NOT_SHIPPED)
        }
    }
}
