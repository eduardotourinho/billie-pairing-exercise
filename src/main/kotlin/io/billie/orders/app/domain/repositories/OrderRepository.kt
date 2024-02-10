package io.billie.orders.app.domain.repositories

import io.billie.orders.app.domain.entities.Order
import java.util.UUID

interface OrderRepository {

    fun createOrder(order: Order)

    fun findByMerchantAndId(merchantId: UUID, orderId: UUID): Order
}
