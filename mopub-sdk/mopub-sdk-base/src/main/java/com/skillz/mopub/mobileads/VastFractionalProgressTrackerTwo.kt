// Copyright 2018-2020 Twitter, Inc.
// Licensed under the MoPub SDK License Agreement
// http://www.mopub.com/legal/sdk-license-agreement/

package com.skillz.mopub.mobileads

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.skillz.mopub.common.Constants
import java.util.regex.Pattern
import kotlin.math.round

class VastFractionalProgressTrackerTwo(
    @Expose @SerializedName(Constants.VAST_TRACKER_TRACKING_FRACTION) val trackingFraction: Float,
    content: String,
    messageType: MessageType,
    isRepeatable: Boolean)
    : VastTrackerTwo(content, messageType, isRepeatable), Comparable<VastFractionalProgressTrackerTwo> {

    data class Builder(private val content: String,
                  private val trackingFraction: Float) {
        private var messageType: MessageType = MessageType.TRACKING_URL
        private var isRepeatable: Boolean = false

        fun messageType(messageType: MessageType) = apply { this.messageType = messageType }
        fun isRepeatable(isRepeatable: Boolean) = apply { this.isRepeatable = isRepeatable }
        fun build() = VastFractionalProgressTrackerTwo(trackingFraction, content, messageType, isRepeatable)
    }

    companion object {
        private const val serialVersionUID: Long = 1L
        private val percentagePattern = Pattern.compile("((\\d{1,2})|(100))%")

        fun isPercentageTracker(progressValue: String?): Boolean {
            return (!progressValue.isNullOrEmpty()
                    && percentagePattern.matcher(progressValue).matches())
        }

        fun parsePercentageOffset(progressValue: String?, videoDuration: Int): Int? {
            return progressValue
                ?.replace("%", "")
                ?.let {
                    round(videoDuration * it.toFloat() / 100f).toInt()
                }
        }
    }

    override fun compareTo(other: VastFractionalProgressTrackerTwo): Int {
        return this.trackingFraction.compareTo(other.trackingFraction)
    }

    override fun toString(): String {
        return "$trackingFraction: $content"
    }

}