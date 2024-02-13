package io.billie.orders.adapters.out.storage.mappers

import io.billie.orders.app.domain.entities.Shipment
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.*

@Component
class ShipmentMapper {

    fun mapShipment() = RowMapper<Shipment> { it: ResultSet, _: Int ->
        Shipment(
            Shipment.ShipmentId(it.getObject("id", UUID::class.java)),
            it.getBigDecimal("total")
        )
    }
}