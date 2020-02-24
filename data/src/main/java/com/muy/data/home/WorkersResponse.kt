package com.muy.data.home

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName
import com.muy.domain.utils.EMPTY_INT
import com.muy.domain.utils.EMPTY_STRING

data class WorkersData(
    val id: Int = EMPTY_INT,
    var fullName: String = EMPTY_STRING,
    val position: String = EMPTY_STRING,
    val salary: String = EMPTY_STRING,
    val phone: String = EMPTY_STRING,
    val email: String = EMPTY_STRING,
    val upperRelation: Int = EMPTY_INT
)

data class WorkersResponse(
    @SerializedName("Carlos Velasquez") val carlosVelasquez: WorkersData = WorkersData(),
    @SerializedName("Mateo Rodriguez") val mateoRodriguez: WorkersData = WorkersData(),
    @SerializedName("Germán Ramirez") val germanRamirez: WorkersData = WorkersData(),
    @SerializedName("Ricardo Zuñiga") val ricardoZuniga: WorkersData = WorkersData(),
    @SerializedName("Oscar Zuluaga") val oscarZuluaga: WorkersData = WorkersData(),
    @SerializedName("Gerardo Alvarez") val gerardoAlvarez: WorkersData = WorkersData(),
    @SerializedName("Miguel Carvajal") val miguelCarvajal: WorkersData = WorkersData(),
    @SerializedName("Carlos Beltrán") val carlosBeltran: WorkersData = WorkersData(),
    @SerializedName("Diego Bermeo") val diegoBermeo: WorkersData = WorkersData(),
    @SerializedName("Fernando Vargas") val fernandoVargas: WorkersData = WorkersData()
)

fun WorkersData.toDatabaseEntity() = WorkerEntity(
    id = id,
    fullName = fullName,
    position = position,
    salary = salary.toInt(),
    phone = phone,
    email = email,
    upperRelation = upperRelation
)
