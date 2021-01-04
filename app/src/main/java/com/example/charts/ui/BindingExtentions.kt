package com.example.charts.ui

import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

/**
 * Extensions for viewBinding - mostly for more accurate and clean look
 */

fun <T : ViewBinding> Fragment.fragmentBinding(viewBindingFactory: (View) -> T) =
    FragmentBinding(this, viewBindingFactory)

inline fun <T : ViewBinding> AppCompatActivity.activityBinding(
    crossinline bindingInflater: (LayoutInflater) -> T
) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

