package com.espoir.shatter.fragment

import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.espoir.shatter.ShatterEvent
import com.espoir.shatter.anyMap
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FmShatterManager(private val fragment: Fragment) : LifecycleEventObserver {

    internal val shatters = mutableListOf<FmShatter>()
    private var fragmentWatcher = ShatterFragmentWatcher(fragment)
    private val newMsgFlow: MutableSharedFlow<ShatterEvent<*>> by lazy {
        MutableSharedFlow()
    }
    val shatterCache = FmShatterCache()

    /**
     * 用来保存数据，方便各个Shatter获取
     */
    internal val dataSaveMap = hashMapOf<String, Any?>()

  init {
      GlobalIShatterFragment.INSTANCE.fragment=fragment
      fragment.viewLifecycleOwner.lifecycle.removeObserver(this)
      fragment.viewLifecycleOwner.lifecycle.addObserver(this)
      fragmentWatcher.install()
      fragment.viewLifecycleOwner.lifecycleScope.launch {
          newMsgFlow.collectLatest {
              event->
              shatters.forEach { it.onShatterEvent(event.key,event.data) }
          }
      }
  }

    open fun saveData(key: String, value: Any?) {
        dataSaveMap[key] = value
    }

    open fun <T> getSaveData(key: String): T? {
        return dataSaveMap[key] as? T?
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        shatters.forEach{it.onStateChanged(source,event)}
        if (event==Lifecycle.Event.ON_DESTROY){
            fragmentWatcher.onDestroy()
            fragmentWatcher.uninstall()
            destroy()
            source.lifecycle.removeObserver(this)
            return
        }
    }

    internal fun sendShatterEvent(key: String, data: Any? = null) {
        fragment.viewLifecycleOwner.lifecycleScope.launch {
            newMsgFlow.emit(ShatterEvent(key, data))
        }
    }

    fun addShatter(@IdRes containViewId: Int, shatter: FmShatter) = apply {
        shatter.shatterManager = this
        val containView = fragment.requireView().findViewById<View>(containViewId)
        addShatter(containView, shatter)
    }

    fun addShatter(containView: View, shatter: FmShatter) = apply {
        shatter.shatterManager = this
        shatter.containView = containView
        shatter.attachFragment(fragment)
        shatters.add(shatter)
        shatterCache.putShatter(shatter)
    }

    fun addShatter(shatter: FmShatter) = apply {
        shatter.shatterManager = this
        shatter.attachFragment(fragment)
        shatters.add(shatter)
        shatterCache.putShatter(shatter)
    }

    fun remove(shatter: FmShatter) {
        shatterCache.removeShatter(shatter.getTag())
        shatters.remove(shatter)
    }
    inline fun <reified T> findShatter(clazz: Class<T>): T? {
        if (clazz.isInterface) {
            val tag = clazz.simpleName
            var shatter = shatterCache.getShatter(tag)
            if (shatter == null) {
                val pair = shatterCache.cacheMap.anyMap { it.value is T } ?: return null
                shatter = pair.second
                shatterCache.cacheMap[tag] = shatter
            }
            return shatter as? T?
        } else {
            val tag = clazz.simpleName
            val shatter = shatterCache.getShatter(tag)
            return shatter as? T?
        }
    }
    fun start() {
        shatters.forEach {
            it.onShatterCreate()
        }
    }
    private fun destroy() {
        shatterCache.clear()
        dataSaveMap.clear()
        shatters.clear()
        GlobalIShatterFragment.INSTANCE.fragment=null
    }

}