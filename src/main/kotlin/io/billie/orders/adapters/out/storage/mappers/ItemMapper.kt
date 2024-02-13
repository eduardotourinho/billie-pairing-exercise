package io.billie.orders.adapters.out.storage.mappers

import io.billie.orders.app.domain.vo.Item
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import java.sql.ResultSet
import java.util.UUID

@Component
class ItemMapper {

    fun mapItems() = RowMapper<Item> { it: ResultSet, _: Int ->
        Item(
            it.getObject("id", UUID::class.java),
            it.getString("item_name"),
            it.getDouble("price")
        )
    }
}
