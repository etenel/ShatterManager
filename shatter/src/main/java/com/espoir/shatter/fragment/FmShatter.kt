package com.espoir.shatter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import com.espoir.shatter.Shatter

open class FmShatter(override val lifecycle: Lifecycle) : FmShatterLifecycleListener, LifecycleOwner {

    companion object {
        const val NO_LAYOUT = 0
    }

    var shatterManager: FmShatterManager? = null
    var containView: View? = null
    private var fra: Fragment? = null
    private var fragmentManager: FragmentManager? = null
    val fragment: Fragment
        get() = if (fra == null) {
            GlobaIShatterFragment.INSTANCE.fragment as Fragment
        } else fra!!

     val lifecycleScope: LifecycleCoroutineScope get() = lifecycle.coroutineScope
    fun attachFragment(fragment: Fragment) {
        onAttachFragment(fragment)
        if (getLayoutResId() != Shatter.NO_LAYOUT && containView != null) {
            if (containView is ViewGroup) {
                val view = LayoutInflater.from(fragment.context).inflate(getLayoutResId(), null)
                (containView as ViewGroup).addView(view)
                containView = view
            }
        }
    }

    private fun onAttachFragment(fragment: Fragment) {
        this.fra = fragment
    }

    fun onShatterCreate() {
        onCreate(fragment.arguments)
        initView(containView, fragment.arguments)
        initData(fragment.arguments)
    }


    open fun onCreate(arguments: Bundle?) {}

    open fun initView(view: View?, arguments: Bundle?) {}

    open fun initData(arguments: Bundle?) {}
    fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
    }


    @LayoutRes
    open fun getLayoutResId(): Int = Shatter.NO_LAYOUT

    open fun getTag(): String = this::class.java.simpleName
    inline fun <reified T> findShatter(clazz: Class<T>): T? {
        return shatterManager?.findShatter(clazz)
    }

    open fun saveData(key: String, value: Any?) {
        shatterManager?.saveData(key, value)
    }

    open fun <T> getSaveData(key: String): T? {
        return shatterManager?.getSaveData(key)
    }

    open fun sendShatterEvent(key: String, data: Any? = null) {
        shatterManager?.sendShatterEvent(key, data)
    }

    open fun onShatterEvent(key: String, data: Any?) {
    }



    override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
        fragmentManager = fm
    }

    override fun onFragmentViewCreated(
        fm: FragmentManager,
        f: Fragment,
        v: View,
        savedInstanceState: Bundle?
    ) {

    }

    override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {

    }

    override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {

    }

    override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {

    }

    override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {

    }

    override fun onFragmentSaveInstanceState(fm: FragmentManager, f: Fragment, outState: Bundle) {

    }

    override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {

    }

    override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
        fragmentManager = null
    }

    override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {

    }

    /**
     * 可以通知到fragment所在的activity
     */
    fun setFragmentParentResult(requestKey:String,bundle: Bundle) {
        fragment.parentFragmentManager.setFragmentResult(requestKey,bundle)
    }

    /**
     * 只能通知到fragment
     */
    fun setFragmentChildResult(requestKey:String,bundle: Bundle) {
        fragment.childFragmentManager.setFragmentResult(requestKey,bundle)
    }
    /**
     * lifecycle的destroy阶段
     */
    open fun onDestroy() {
    }


}