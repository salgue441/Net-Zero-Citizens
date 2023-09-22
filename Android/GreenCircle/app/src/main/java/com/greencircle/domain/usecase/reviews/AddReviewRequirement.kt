package com.greencircle.domain.usecase.reviews

import com.greencircle.data.repository.ReviewRepository
import com.greencircle.domain.model.reviews.ReviewBase
import java.util.UUID
import okhttp3.ResponseBody
import retrofit2.Response

class AddReviewRequirement {
    private val repository = ReviewRepository()

    suspend operator fun invoke(
        userId: UUID,
        companyId: UUID,
        review: ReviewBase
    ): Response<ResponseBody>? = repository.addReview(userId, companyId, review)
}