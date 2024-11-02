package com.espoir.shatter.fragment

class FmShatterCache {

    val cacheMap = hashMapOf<String, FmShatter>()

    fun putShatter(shatter: FmShatter) {
        cacheMap[shatter.getTag()] = shatter
    }

    fun getShatter(tag: String): FmShatter? {
        return cacheMap[tag]
    }

    fun removeShatter(tag: String) {
        cacheMap.remove(tag)
    }

    fun clear() {
        cacheMap.clear()
    }
}