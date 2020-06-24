package com.example.posteey.utils

import android.view.View
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.displayShimmer() {
    this.visibility = View.VISIBLE
    this.startShimmer()
}

fun ShimmerFrameLayout.clearShimmer() {
    this.visibility = View.GONE
    this.stopShimmer()
}
