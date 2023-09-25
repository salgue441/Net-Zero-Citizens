package com.greencircle.framework.views.fragments.reviews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.greencircle.R
import com.greencircle.databinding.FragmentReviewFormBinding
import com.greencircle.domain.model.reviews.ReviewBase
import com.greencircle.framework.viewmodel.reviews.ReviewFormViewModel
import com.greencircle.framework.views.fragments.catalogue.CatalogueFragment
import java.util.UUID

class ReviewFormFragment : Fragment() {
    private var _binding: FragmentReviewFormBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: ReviewFormViewModel

    private var rating: Float = 0.0f
    private var reviewTitle: String? = null
    private var review: String? = null
    private lateinit var companyId: UUID
    private lateinit var userId: UUID

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[ReviewFormViewModel::class.java]

        _binding = FragmentReviewFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (arguments?.getString("CompanyId") == null) {
            companyId = UUID.fromString("c1b0e7e0-0b1a-4e1a-9f1a-0e5a9a1b0e7e")
        } else {
            companyId = UUID.fromString(arguments?.getString("CompanyId"))
        }

        if (arguments?.getString("userId") == null) {
            userId = UUID.fromString("8de45630-2e76-4d97-98c2-9ec0d1f3a5b8")
        } else {
            userId = UUID.fromString(arguments?.getString("userId"))
        }

        initializeRatingBar()
        initializeRatingBarListener()
        initializePublishReviewButton()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeRatingBar() {
        rating = arguments?.getFloat("RatingStars") ?: 0.0f
        binding.ratingBar.rating = rating
    }

    private fun initializeRatingBarListener() {
        binding.ratingBar.setOnRatingBarChangeListener { _, _, _ ->
            binding.ratingError.visibility = View.GONE
        }
    }

    private fun setTextInputError(textField: TextInputLayout, errorMessage: String) {
        textField.isErrorEnabled = true
        textField.error = errorMessage
        initializeTextWatchers()
    }

    private fun clearTextFieldsErrors() {
        binding.titleTextField.isErrorEnabled = false
        binding.reviewTextField.isErrorEnabled = false
    }

    private fun isReviewComplete(): Boolean {
        val reviewTitleString = binding.titleInput.text.toString()
        val reviewString = binding.reviewInput.text.toString()

        if (reviewTitleString.isBlank() && reviewString.isNotBlank()) {
            setTextInputError(
                binding.titleTextField,
                getString(R.string.no_title_form_review_error)
            )
            return false
        } else if (reviewTitleString.isNotBlank() && reviewString.isBlank()) {
            setTextInputError(
                binding.reviewTextField,
                getString(R.string.no_review_form_review_error)
            )
            return false
        } else if (reviewTitleString.isBlank() && reviewString.isBlank()) {
            reviewTitle = null
            review = null
            return true
        }

        clearTextFieldsErrors()
        reviewTitle = reviewTitleString
        review = reviewString
        return true
    }

    private fun initializeTextWatchers() {
        binding.titleInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(titleInputEditable: Editable) {
                isReviewComplete()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        binding.reviewInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(reviewInputEditable: Editable) {
                isReviewComplete()
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun navigateToCompanyReviewFragment() {
        val catalogueFragment = CatalogueFragment()
        val fragmentManager = requireActivity().supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.setCustomAnimations(
            R.animator.slide_in_right,
            R.animator.slide_out_left,
            R.animator.slide_in_left,
            R.animator.slide_out_right
        )

        fragmentTransaction.replace(R.id.frame_layout, catalogueFragment)
        fragmentTransaction.commit()
    }

    private fun postReview(rating: Int) {
        val reviewBase = ReviewBase(reviewTitle, review, rating)
        val res = runCatching { viewModel.addReview(userId, companyId, reviewBase) }

        if (res.isFailure) {
            Log.e("ReviewFormFragment", res.exceptionOrNull()?.message.toString())
            Toast.makeText(
                requireContext(),
                getString(R.string.review_submit_error),
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        Toast.makeText(
            requireContext(),
            getString(R.string.review_submit_toast),
            Toast.LENGTH_SHORT
        ).show()

        navigateToCompanyReviewFragment()
    }

    private fun publishReview() {
        val rating = binding.ratingBar.rating.toInt()
        val isReviewComplete = isReviewComplete()

        if (rating == 0) {
            binding.ratingError.visibility = View.VISIBLE
        } else if (isReviewComplete) {
            postReview(rating)
        }
    }

    private fun initializePublishReviewButton() {
        val publishReviewButton = binding.publishReviewButton

        publishReviewButton.setOnClickListener { publishReview() }
    }
}