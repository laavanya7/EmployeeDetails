package com.first.employee.retrofitservice

import com.first.employee.Employe
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("v3/325512ea-9fd0-4aeb-9cf5-cfda5c96f7a0")
    fun getEmployee(): Single<Employe>

}