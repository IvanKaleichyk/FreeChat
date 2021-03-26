package com.koleychik.module_injector

import androidx.fragment.app.Fragment

object NavigationSystem {

    var onStartFeature: ((fragment: Fragment) -> Unit)? = null

}