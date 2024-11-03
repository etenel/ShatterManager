package com.espoir.shatter.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks

enum class GlobalIShatterFragment {
    INSTANCE;

    var fragment: Fragment? = null



    fun registerFragmentLifecycleCallbacks(callback: FragmentLifecycleCallbacks) {
        fragment?.parentFragmentManager?.registerFragmentLifecycleCallbacks(callback, true)

    }

    fun unregisterFragmentLifecycleCallbacks(callback: FragmentLifecycleCallbacks) {
        fragment?.parentFragmentManager?.unregisterFragmentLifecycleCallbacks(callback)

    }
}