package com.muy.data.Service

import com.muy.data.home.WorkersResponse
import retrofit2.Response
import retrofit2.http.GET

interface MuyServiceAPI {

    @GET("/slozanomuy/Services/master/RH.json")
    suspend fun getWorkers(): Response<WorkersResponse>

}