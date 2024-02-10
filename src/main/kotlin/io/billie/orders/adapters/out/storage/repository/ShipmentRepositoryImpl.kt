package io.billie.orders.adapters.out.storage.repository

import io.billie.orders.app.domain.repositories.ShipmentRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class ShipmentRepositoryImpl: ShipmentRepository {


    override fun addShippedItems(orderId: UUID, items: List<UUID>) {
        TODO("Not yet implemented")
    }
}