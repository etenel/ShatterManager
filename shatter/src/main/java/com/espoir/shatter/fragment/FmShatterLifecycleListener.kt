package com.espoir.shatter.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

interface FmShatterLifecycleListener {

    fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    )


    fun onFragmentStarted(fm: FragmentManager, f: Fragment)


    fun onFragmentResumed(fm: FragmentManager, f: Fragment)


    fun onFragmentPaused(fm: FragmentManager, f: Fragment)


    fun onFragmentStopped(fm: FragmentManager, f: Fragment)


    fun onFragmentSaveInstanceState(
        fm: FragmentManager,
        f: Fragment,
        outState: Bundle
    )


    fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment)


    fun onFragmentDestroyed(fm: FragmentManager, f: Fragment)




}