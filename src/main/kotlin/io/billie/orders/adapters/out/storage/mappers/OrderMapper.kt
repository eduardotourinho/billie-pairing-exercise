package io.billie.orders.adapters.out.storage.mappers


import io.billie.orders.app.domain.entities.Order
import io.billie.orders.app.domain.vo.OrderStatus
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.*

@Component
class OrderMapper {

    fun mapOrder() = RowMapper<Order> { it: ResultSet, _: Int ->
        Order(
            it.getObject("id", UUID::class.java),
            it.getObject("merchant_id", UUID::class.java),
            listOf(),
            it.getDouble("total"),
            it.getObject("state", OrderStatus::class.java)
        )
    }
}