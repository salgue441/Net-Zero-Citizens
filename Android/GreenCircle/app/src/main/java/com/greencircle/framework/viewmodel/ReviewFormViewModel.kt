package com.greencircle.framework.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greencircle.domain.model.ReviewBase
import com.greencircle.domain.usecase.AddReviewRequirement
import java.util.UUID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewFormViewModel : ViewModel() {
    private val addReviewRequirement = AddReviewRequirement()

    fun addReview(UUID: UUID, companyId: UUID, review: ReviewBase) {
        viewModelScope.launch(Dispatchers.IO) { addReviewRequirement(UUID, companyId, review) }
    }
}
