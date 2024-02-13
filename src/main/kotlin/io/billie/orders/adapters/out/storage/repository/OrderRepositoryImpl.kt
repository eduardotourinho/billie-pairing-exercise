package io.billie.orders.adapters.out.storage.repository

import io.billie.orders.adapters.out.storage.mappers.ItemMapper
import io.billie.orders.adapters.out.storage.mappers.OrderMapper
import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.repositories.OrderRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class OrderRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate,
    private val orderMapper: OrderMapper,
    private val itemMMapper: ItemMapper
) : OrderRepository {

    @Transactional()
    override fun createOrder(order: Order): Order {
        jdbcTemplate.update { connection ->
            val statement = connection.prepareStatement(
                "INSERT INTO orders_schema.orders (id, merchant_id, total, state)" +
                        "VALUES (?, ?, ?, ?)",
            )
            statement.setObject(1, order.id.value)
            statement.setObject(2, order.merchantId)
            statement.setBigDecimal(3, order.total)
            statement.setString(4, order.status.toString())

            statement
        }

        order.items.forEach { item ->
            jdbcTemplate.update { connection ->
                val statement = connection.prepareStatement(
                    "INSERT INTO orders_schema.order_items (order_id, item_id)" +
                            "VALUES (?, ?)"
                )

                statement.setObject(1, order.id.value)
                statement.setObject(2, item.id)

                statement
            }
        }

        return findByMerchantAndId(order.merchantId, order.id)
    }

    @Transactional(readOnly = true)
    override fun findByMerchantAndId(merchantId: UUID, orderId: Order.OrderId): Order {
        val order = jdbcTemplate.query(
            "SELECT id, merchant_id, total, state " +
                    "FROM orders_schema.orders o " +
                    "WHERE o.id = ? AND o.merchant_id = ?",
            orderMapper.mapOrder(),
            orderId.value, merchantId
        ).firstOrNull()

        if (order == null) {
            throw Exception("Order not found")
        }

        val items = jdbcTemplate.query(
            "SELECT i.id, i.item_name, i.price FROM orders_schema.items i " +
                    "INNER JOIN orders_schema.order_items oi ON oi.item_id = i.id " +
                    "WHERE oi.order_id = ?",
            itemMMapper.mapItems(),
            orderId.value
        )

        return Order(order.id, order.merchantId, items, order.total, order.status)
    }
}
