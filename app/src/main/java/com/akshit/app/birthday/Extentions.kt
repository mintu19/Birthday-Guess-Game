package com.akshit.app.birthday

import android.content.SharedPreferences
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RawRes

fun ViewGroup.inflate(layoutRes: Int): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, false)
}

inline fun <reified T: Any> SharedPreferences.saveArray(arrayList: List<T>, name: String) {
    val editor = edit()
    val set = arrayList.mapIndexed { index, t ->
        index.toString() + "_" + t.toString()
    }.toSet()
    editor.putStringSet(name, set)
    editor.apply()
}

inline fun <reified T: Any> SharedPreferences.getArray(name: String, default: T) : MutableList<T> {
    val set = getStringSet(name, null)?.sortedBy { it.split('_')[0].toInt() }
    return set?.map {
        val data = it.split("_")[1]
        val y = when(default) {
            is Boolean -> data.toBoolean()
            is Int -> data.toInt()
            is Float -> data.toFloat()
            is Double -> data.toDouble()
            is Long -> data.toLong()
            else -> data
        }
        val z = y as? T
        z ?: default
    }?.toMutableList() ?: mutableListOf()
}

fun Resources.getRawTextFile(@RawRes id: Int) =
    openRawResource(id).bufferedReader().use { it.readText() }