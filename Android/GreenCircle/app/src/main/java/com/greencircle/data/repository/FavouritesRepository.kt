package com.greencircle.data.repository

import com.greencircle.data.remote.favourites.FavouritesAPIClient
import com.greencircle.domain.model.favourites.FavouriteRequest
import com.greencircle.domain.model.favourites.FavouriteResponse
import java.util.UUID

class FavouritesRepository {
    private val api = FavouritesAPIClient()
    suspend fun getAllFavouritesByUser(authToken: String, userId: UUID): FavouriteResponse ? {
        return api.getAllFavouritesByUser(authToken, userId)
    }

    suspend fun markAsFavourite(authToken: String, params: FavouriteRequest) {
        api.markAsFavourite("Bearer $authToken", params)
    }
}