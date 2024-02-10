package io.billie.orders.adapters.out.storage.repository

import io.billie.orders.adapters.out.storage.mappers.ItemMapper
import io.billie.orders.adapters.out.storage.mappers.OrderMapper
import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.repositories.OrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class OrderRepositoryImpl : OrderRepository {

    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate

    @Autowired
    lateinit var orderMapper: OrderMapper

    @Autowired
    lateinit var itemMMapper: ItemMapper

    @Transactional()
    override fun createOrder(order: Order) {
        jdbcTemplate.update { connection ->
            val statement = connection.prepareStatement(
                "INSERT INTO orders_schema.orders (id, merchant_id, total, state)" +
                        "VALUES (?, ?, ?, ?)",
            )
            statement.setObject(1, order.id)
            statement.setObject(2, order.merchantId)
            statement.setDouble(3, order.total)
            statement.setString(4, order.status.toString())

            statement
        }

        order.items.forEach { item ->
            jdbcTemplate.update { connection ->
                val statement = connection.prepareStatement(
                    "INSERT INTO orders_schema.order_items (order_id, item_id)" +
                            "VALUES (?, ?)"
                )

                statement.setObject(1, order.id)
                statement.setObject(2, item.id)

                statement
            }
        }
    }

    @Transactional(readOnly = true)
    override fun findByMerchantAndId(merchantId: UUID, orderId: UUID): Order {
        val order = jdbcTemplate.query(
            "SELECT id, merchant_id, total, state FROM orders_schema.orders WHERE o.id = ? AND o.merchant_id = ?",
            orderMapper.mapOrder(),
            orderId, merchantId
        ).firstOrNull()

        if (order == null) {
            throw Exception("Order not found")
        }

        val items = jdbcTemplate.query(
            "SELECT i.id, i.name, i.price FROM orders_schema.items i " +
                    "INNER JOIN orders_schema orders_items oi ON oi.item_id = i.id " +
                    "WHERE oi.order_id = ?",
            itemMMapper.mapItems(),
            orderId
        )

        return Order(order.id, order.merchantId, items, order.total, order.status)
    }
}
