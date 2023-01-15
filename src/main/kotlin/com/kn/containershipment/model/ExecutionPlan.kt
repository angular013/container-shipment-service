package com.kn.containershipment.model

import jakarta.persistence.*

/**
 * ExecutionPlan is used to store information about shipment and its corresponding actions to be executed.
 */

@Entity
@Table
data class ExecutionPlan(

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    var origin: String? = null,

    var destination: String? = null,

    var customerId: String? = null,

    var transportType: TransportType? = null,

    @ManyToOne(cascade = [CascadeType.ALL], fetch= FetchType.LAZY)
    @JoinColumn(name = "fk_temperature_range_id")
    var temperature: TemperatureRange? = null,

    var fragile: Boolean = false,

    var notifyCustomer: Boolean = false,

    var templateId: Long = 0,

    @OneToMany(targetEntity = ExecutionPlanAction::class, cascade = [CascadeType.ALL], fetch=FetchType.EAGER)
    @JoinColumn(name = "exa_fk", referencedColumnName = "id")
    var actions: MutableList<ExecutionPlanAction> = mutableListOf(),
)

/**
 * ExecutionPlanAction is used to execution individual actions from the template actions in an ExecutionPlan
 */
@Entity
@Table
data class ExecutionPlanAction(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long = 0,

    val actionName: String? = null,

    val isExecuted: Boolean = false,

    val isNotify: Boolean = false
)
