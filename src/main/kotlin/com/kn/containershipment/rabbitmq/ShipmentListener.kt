package com.kn.containershipment.rabbitmq

import com.kn.containershipment.model.Shipment
import org.springframework.amqp.core.ExchangeTypes
import org.springframework.amqp.rabbit.annotation.Exchange
import org.springframework.amqp.rabbit.annotation.Queue
import org.springframework.amqp.rabbit.annotation.QueueBinding
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ShipmentListener {

    @Autowired
    private lateinit var shipmentService: ShipmentService

    @RabbitListener(bindings = [QueueBinding(value = Queue("shipment"), exchange = Exchange("amq.fanout", type = ExchangeTypes.FANOUT))],
                    containerFactory = "jsonFactory")
    fun receiveShipment(shipment: Shipment) {
        shipmentService.processShipment(shipment)
    }

}