package io.billie.orders.app.domain.vo

import java.util.*

data class Item(
    val id: UUID,
    val itemName: String = "",
    val price: Double = 0.0
) {

    override fun equals(other: Any?): Boolean {
        if (other !is Item) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    companion object Factory {

        fun create(id: UUID): Item {
            return Item(id)
        }
    }
}
