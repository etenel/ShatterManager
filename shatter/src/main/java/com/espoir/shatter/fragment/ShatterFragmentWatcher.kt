package com.espoir.shatter.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks

class ShatterFragmentWatcher(val curFra: Fragment) {
    companion object {
        const val TAG = "ShatterFragmentWatcher"
    }

    private val lifecycleCallbacks = object : FragmentLifecycleCallbacks() {


        override fun onFragmentCreated(
            fm: FragmentManager,
            f: Fragment,
            savedInstanceState: Bundle?
        ) {
            GlobaIShatterFragment.INSTANCE.fragment = f
        }


        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            if (curFra == f) {
                dispatch { fmShatterManager ->
                    fmShatterManager.shatters.forEach {
                        it.onFragmentViewCreated(
                            fm,
                            f,
                            v,
                            savedInstanceState
                        )
                    }
                }
            }
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            if (curFra == f) {
                dispatch { fmShatterManager ->
                    fmShatterManager.shatters.forEach {
                        it.onFragmentStarted(fm, f)
                    }
                }
            }
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentResumed(fm,f)
                } }
            }
        }

        override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager: FmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentPaused(fm, f)
                } }
            }
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentStopped(fm, f)
                } }
            }
        }

        override fun onFragmentSaveInstanceState(
            fm: FragmentManager,
            f: Fragment,
            outState: Bundle
        ) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentSaveInstanceState(fm, f,outState)
                } }
            }
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentViewDestroyed(fm, f)
                } }
            }
        }

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentDestroyed(fm, f)
                } }
            }
        }

    }

fun onDestroy(){
    GlobaIShatterFragment.INSTANCE.fragment=null
    dispatch {
        fmShatterManager -> fmShatterManager.shatters.forEach { it.onDestroy() }
    }
}
    fun dispatch(callback: (FmShatterManager) -> Unit) {
        if (curFra is IShatterFragment) {
            callback.invoke(curFra.getShatterManager())
        }
    }

    fun install() {
        GlobaIShatterFragment.INSTANCE.registerFragmentLifecycleCallbacks(lifecycleCallbacks)
    }

    fun uninstall() {
        GlobaIShatterFragment.INSTANCE.unregisterFragmentLifecycleCallbacks(lifecycleCallbacks)
    }

}