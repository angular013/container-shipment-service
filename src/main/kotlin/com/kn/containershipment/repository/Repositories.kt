package com.kn.containershipment.repository

import com.kn.containershipment.model.*
import jakarta.persistence.EntityManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.CrudRepository

interface TemplateRepository : CrudRepository<PlanTemplate, Long>
interface ActionRepository : CrudRepository<Action, Long>
interface TemperatureRangeRepository : CrudRepository<TemperatureRange, Long>
interface ExecutionPlanRepository : CrudRepository<ExecutionPlan, Long>
@Autowired
lateinit var entityManager: EntityManager
fun saveAndFlush(plan: ExecutionPlan) {
    entityManager.merge(plan)
    entityManager.flush()
}
interface ExecutionPlanActionRepository : CrudRepository<ExecutionPlanAction, Long>