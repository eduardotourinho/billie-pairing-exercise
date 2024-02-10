package io.billie.orders.app.services

import io.billie.orders.app.domain.entities.Shipment
import io.billie.orders.app.domain.services.ShipmentValidator
import io.billie.orders.app.domain.vo.Item
import io.billie.orders.app.ports.`in`.commands.ShipmentNotificationCommand
import io.billie.orders.app.ports.`in`.NotifyOrderShipmentUseCase
import io.billie.orders.app.ports.out.ShipmentNotifierPort
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OrderShipmentManager : NotifyOrderShipmentUseCase {

    @Autowired
    private lateinit var shipmentValidator: ShipmentValidator

    @Autowired
    private lateinit var shipmentNotifierPort: ShipmentNotifierPort


    override fun notifyShipment(command: ShipmentNotificationCommand) {

        val items = command.items.map { item -> Item.create(item) }
        val shipment = Shipment.new(command.orderId, items)

        if (!shipmentValidator.isValid(shipment)) {
            throw Exception("Shipment is not valid")
        }

        shipmentNotifierPort.notifyShipment(shipment)
    }
}
