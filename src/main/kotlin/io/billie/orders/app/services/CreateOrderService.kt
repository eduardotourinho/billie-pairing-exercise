package io.billie.orders.app.services

import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.repositories.OrderRepository
import io.billie.orders.app.domain.vo.Item
import io.billie.orders.app.ports.`in`.CreateOrderUseCase
import org.springframework.stereotype.Service

@Service
class CreateOrderService(
    private val orderRepository: OrderRepository
) : CreateOrderUseCase {

    override fun createOrder(newOrderCommand: CreateOrderUseCase.NewOrderCommand): Order {
        val items = newOrderCommand.items.map { item -> Item.create(item) }
        val order = Order.new(newOrderCommand.merchantId, items, newOrderCommand.total)

        return orderRepository.createOrder(order)
    }
}
