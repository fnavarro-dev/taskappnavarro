package com.example.taskappnavarro.model.retrofit

import retrofit2.Response
import retrofit2.http.GET

//Importante recalcar el uso de punto para lograr obtener la data desde el endpoint raíz
interface TaskApiClient {
    @GET(".")
    suspend fun getData(): Response<TaskResponse>
}