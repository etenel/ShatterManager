package com.espoir.shatter.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks

enum class GlobaIShatterFragment {
    INSTANCE;
    var fragment: Fragment? = null


    fun registerFragmentLifecycleCallbacks(callback: FragmentLifecycleCallbacks) {
        fragment?.childFragmentManager?.registerFragmentLifecycleCallbacks(callback, true)

    }

    fun unregisterFragmentLifecycleCallbacks(callback: FragmentLifecycleCallbacks) {
        fragment?.childFragmentManager?.unregisterFragmentLifecycleCallbacks(callback)

    }
}