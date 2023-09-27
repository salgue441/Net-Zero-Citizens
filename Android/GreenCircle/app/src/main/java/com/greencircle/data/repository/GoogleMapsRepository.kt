package com.greencircle.data.repository

import com.greencircle.data.remote.googlemaps.GoogleMapsAPIService
import com.greencircle.data.remote.googlemaps.NetworkModuleGoogleMaps
import com.greencircle.domain.model.googlemaps.CompanyObject

class GoogleMapsRepository() {
    private lateinit var api: GoogleMapsAPIService
    suspend fun getCompanyList(): CompanyObject? {
        api = NetworkModuleGoogleMaps()
        return try {
            api.getCompanyList()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            null
        }
    }
}