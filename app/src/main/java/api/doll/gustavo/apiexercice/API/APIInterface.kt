package api.doll.gustavo.apiexercice.API

import api.doll.gustavo.apiexercice.API.APIResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface APIInterface {

    @GET("/api/?results=20")
    fun getUsers(): Observable<APIResponse>
}