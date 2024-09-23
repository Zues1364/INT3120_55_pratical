package com.example.a30dayapp.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.a30dayapp.R

data class Day(
    @StringRes val numberOfDay: Int,
    @StringRes val dayTitle: Int,
    @DrawableRes val imageRes: Int,
    @StringRes val dayDescription: Int,
)
val days = listOf(
    Day(R.string.number_of_day_1, R.string.day_title_1, R.drawable.image_1, R.string.day_description_1),
    Day(R.string.number_of_day_2, R.string.day_title_2, R.drawable.image_2, R.string.day_description_2),
    Day(R.string.number_of_day_3, R.string.day_title_3, R.drawable.image_3, R.string.day_description_3),
    Day(R.string.number_of_day_4, R.string.day_title_4, R.drawable.image_4, R.string.day_description_4),
    Day(R.string.number_of_day_5, R.string.day_title_5, R.drawable.image_5, R.string.day_description_5),
    Day(R.string.number_of_day_6, R.string.day_title_6, R.drawable.image_6, R.string.day_description_6),
    Day(R.string.number_of_day_7, R.string.day_title_7, R.drawable.image_7, R.string.day_description_7),
    Day(R.string.number_of_day_8, R.string.day_title_8, R.drawable.image_8, R.string.day_description_8),
    Day(R.string.number_of_day_9, R.string.day_title_9, R.drawable.image_9, R.string.day_description_9),
    Day(R.string.number_of_day_10, R.string.day_title_10, R.drawable.image_10, R.string.day_description_10),
    Day(R.string.number_of_day_11, R.string.day_title_11, R.drawable.image_11, R.string.day_description_11),
    Day(R.string.number_of_day_12, R.string.day_title_12, R.drawable.image_12, R.string.day_description_12),
    Day(R.string.number_of_day_13, R.string.day_title_13, R.drawable.image_13, R.string.day_description_13),
    Day(R.string.number_of_day_14, R.string.day_title_14, R.drawable.image_14, R.string.day_description_14),
    Day(R.string.number_of_day_15, R.string.day_title_15, R.drawable.image_15, R.string.day_description_15),
    Day(R.string.number_of_day_16, R.string.day_title_16, R.drawable.image_16, R.string.day_description_16),
    Day(R.string.number_of_day_17, R.string.day_title_17, R.drawable.image_17, R.string.day_description_17),
    Day(R.string.number_of_day_18, R.string.day_title_18, R.drawable.image_18, R.string.day_description_18),
    Day(R.string.number_of_day_19, R.string.day_title_19, R.drawable.image_19, R.string.day_description_19),
    Day(R.string.number_of_day_20, R.string.day_title_20, R.drawable.image_20, R.string.day_description_20),
    Day(R.string.number_of_day_21, R.string.day_title_21, R.drawable.image_21, R.string.day_description_21),
    Day(R.string.number_of_day_22, R.string.day_title_22, R.drawable.image_22, R.string.day_description_22),
    Day(R.string.number_of_day_23, R.string.day_title_23, R.drawable.image_23, R.string.day_description_23),
    Day(R.string.number_of_day_24, R.string.day_title_24, R.drawable.image_24, R.string.day_description_24),
    Day(R.string.number_of_day_25, R.string.day_title_25, R.drawable.image_25, R.string.day_description_25),
    Day(R.string.number_of_day_26, R.string.day_title_26, R.drawable.image_26, R.string.day_description_26),
    Day(R.string.number_of_day_27, R.string.day_title_27, R.drawable.image_27, R.string.day_description_27),
    Day(R.string.number_of_day_28, R.string.day_title_28, R.drawable.image_28, R.string.day_description_28),
    Day(R.string.number_of_day_29, R.string.day_title_29, R.drawable.image_29, R.string.day_description_29),
    Day(R.string.number_of_day_30, R.string.day_title_30, R.drawable.image_30, R.string.day_description_30)
)


