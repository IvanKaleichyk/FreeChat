package com.kaleichyk.utils.navigation

import androidx.fragment.app.Fragment


object NavigationSystem {

    var onStartFeature: ((fragment: Fragment) -> Unit)? = null
}