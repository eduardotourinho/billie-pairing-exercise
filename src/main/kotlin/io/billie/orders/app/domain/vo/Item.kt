package io.billie.orders.app.domain.vo

import java.util.*

data class Item(
    val id: UUID,
    val itemName: String = "",
    val price: Double = 0.0
) {

    companion object Factory {

        fun create(id: UUID): Item {
            return Item(id)
        }
    }
}
