package com.espoir.shatter.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentManager.FragmentLifecycleCallbacks
import com.espoir.shatter.LogUtils

class ShatterFragmentWatcher(val curFra: Fragment) {
    companion object {
        const val TAG = "ShatterFragmentWatcher"
    }

    private val lifecycleCallbacks = object : FragmentLifecycleCallbacks() {





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
                LogUtils.i("onFragmentViewCreated")
            }
        }

        override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
            if (curFra == f) {
                dispatch { fmShatterManager ->
                    fmShatterManager.shatters.forEach {
                        it.onFragmentStarted(fm, f)
                    }
                }
                LogUtils.i("onFragmentStarted")

            }
        }

        override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentResumed(fm,f)
                } }
                LogUtils.i("onFragmentResumed")

            }
        }

        override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager: FmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentPaused(fm, f)
                } }
                LogUtils.i("onFragmentPaused")
            }
        }

        override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentStopped(fm, f)
                } }
                LogUtils.i("onFragmentStopped")
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
                LogUtils.i("onFragmentSaveInstanceState")
            }
        }

        override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentViewDestroyed(fm, f)
                } }
                LogUtils.i("onFragmentViewDestroyed")

            }
        }

        override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
            if (curFra==f){
                dispatch { fmShatterManager -> fmShatterManager.shatters.forEach {
                    it.onFragmentDestroyed(fm, f)
                } }
                LogUtils.i("onFragmentDestroyed")

            }
        }

    }

fun onDestroy(){
    GlobalIShatterFragment.INSTANCE.fragment=null
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
        GlobalIShatterFragment.INSTANCE.registerFragmentLifecycleCallbacks(lifecycleCallbacks)
    }

    fun uninstall() {
        GlobalIShatterFragment.INSTANCE.unregisterFragmentLifecycleCallbacks(lifecycleCallbacks)
    }

}