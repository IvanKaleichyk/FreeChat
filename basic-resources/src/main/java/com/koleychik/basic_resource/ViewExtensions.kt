package com.koleychik.basic_resource

import android.view.ViewGroup
import androidx.core.view.children

fun ViewGroup.isEnabledViews(value: Boolean) {
    for (i in children) i.isEnabled = value
}