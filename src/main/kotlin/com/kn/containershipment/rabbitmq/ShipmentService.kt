package com.kn.containershipment.rabbitmq

import com.kn.containershipment.model.*
import com.kn.containershipment.repository.ExecutionPlanActionRepository
import com.kn.containershipment.repository.ExecutionPlanRepository
import com.kn.containershipment.repository.TemplateRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*

@Service
@RestController
@RequestMapping("/api/shipments")
@CrossOrigin(origins = ["http://localhost:4200"])
class ShipmentService {

    @Autowired
    private lateinit var executionPlanRepository: ExecutionPlanRepository
    @Autowired
    private lateinit var planTemplateRepository: TemplateRepository
    @Autowired
    private lateinit var executionPlanActionRepository: ExecutionPlanActionRepository
    @PersistenceContext
    private lateinit var entityManager: EntityManager

    @Transactional
    fun processShipment(shipment: Shipment) {
        val template = getTemplate() // Get template by id
        println(template)
        val executionPlan = mergeShipmentAndTemplate(shipment, template)
        println(executionPlan)

        if(executionPlanRepository == null) {
            println("executionPlanRepository is null")
        } else {
            println("executionPlanRepository is not null")
            //executionPlanRepository.saveAndFlush(executionPlan)
            entityManager.merge(executionPlan)
            entityManager.flush()
        }
    }

    @PostMapping
    @Transactional
    fun processShipmentPostRequest(@RequestBody shipments: MutableList<Shipment>) {

        for (shipment in shipments) {
            val template = getTemplate() // Get template by id
            println(template)
            val executionPlan = mergeShipmentAndTemplate(shipment, template)
            println(executionPlan)

            if(executionPlanRepository == null) {
                println("executionPlanRepository is null")
            } else {
                println("executionPlanRepository is not null")
                //executionPlanRepository.saveAndFlush(executionPlan)
                entityManager.merge(executionPlan)
                entityManager.flush()
            }
        }

    }


    private fun mergeShipmentAndTemplate(shipment: Shipment, template: PlanTemplate): ExecutionPlan {
        val executionActions = template.actions.map {
            executionPlanActionRepository.save(ExecutionPlanAction(actionName = it.name, isExecuted = false, isNotify = false)) }.toMutableList()
        val temperatureRange = shipment.temperatureRange?.min?.let { TemperatureRange(min = it, max = shipment.temperatureRange.max) }
        return ExecutionPlan(
            id = 0,
            origin = shipment.origin,
            destination = shipment.destination,
            customerId = shipment.customerId,
            transportType = shipment.transportType,
            temperature = temperatureRange,
            fragile = shipment.fragile,
            notifyCustomer = shipment.notifyCustomer,
            templateId = template.id,
            actions = executionActions
        )

    }


    private fun getTemplate(): PlanTemplate {
        val count = planTemplateRepository.count()
        if (count > 0) {
            return planTemplateRepository.findAll().first()
        } else {
            throw IllegalArgumentException("Template not found")
        }
    }
}



