package io.billie.orders.adapters.out.storage.repository

import io.billie.orders.adapters.out.storage.mappers.ItemMapper
import io.billie.orders.adapters.out.storage.mappers.ShipmentMapper
import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.repositories.OrderRepository
import io.billie.orders.app.domain.repositories.ShipmentRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
class ShipmentRepositoryImpl(
    private val jdbcTemplate: JdbcTemplate,
    private val shipmentMapper: ShipmentMapper,
    private val orderRepository: OrderRepository,
    private val itemMapper: ItemMapper,
) : ShipmentRepository {


    @Transactional
    override fun insertShipment(shipment: Shipment): Shipment {
        jdbcTemplate.update { connection ->
            val statement = connection.prepareStatement(
                "INSERT INTO orders_schema.shipments (id, order_id, total)" +
                        "VALUES (?, ?, ?)",
            )
            statement.setObject(1, shipment.id.value)
            statement.setObject(2, shipment.order!!.id.value)
            statement.setBigDecimal(3, shipment.total)

            statement
        }

        shipment.shippedItems?.forEach { item ->
            jdbcTemplate.update { connection ->
                val statement = connection.prepareStatement(
                    "INSERT INTO orders_schema.shipped_items (shipment_id, item_id)" +
                            "VALUES (?, ?)"
                )

                statement.setObject(1, shipment.id.value)
                statement.setObject(2, item.id)

                statement
            }
        }

        return shipment
    }

    @Transactional(readOnly = true)
    override fun findByOrder(orderId: Order.OrderId, merchantId: UUID): List<Shipment> {
        val order = orderRepository.findByMerchantAndId(merchantId, orderId)

        val shipments = jdbcTemplate.query(
            "SELECT id, total FROM orders_schema.shipments s WHERE s.order_id = ?",
            shipmentMapper.mapShipment(),
            orderId.value
        )

        return shipments.map { shipment ->
            val items = jdbcTemplate.query(
                "SELECT i.id, i.item_name, i.price FROM orders_schema.items i " +
                        "INNER JOIN orders_schema.shipped_items si ON si.item_id = i.id " +
                        "WHERE si.shipment_id = ?",
                itemMapper.mapItems(),
                shipment.id.value
            ).toList()

            Shipment(shipment.id, shipment.total, items, order)
        }
    }
}