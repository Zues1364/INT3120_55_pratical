package com.example.gridapp.model

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Topic(
    @StringRes val stringResourceId: Int,
    val lessonCount: Int,
    @DrawableRes val imageResourceId: Int,
)
