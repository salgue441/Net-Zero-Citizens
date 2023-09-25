package com.greencircle.data.remote.catalogue

import com.greencircle.domain.model.catalogue.CatalogueResponse
import retrofit2.http.GET

interface CatalogueAPIService {
    @GET("company/")
    suspend fun getCatalogue(): CatalogueResponse
}