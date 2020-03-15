package it.alexm.mvvmexample.data

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE


/**
 * Created by alexm on 15/03/2020
 */
fun <V : View> V.setVisible(visible: Boolean?, visibleAction: (V) -> Unit = {}) {
    this.visibility = if (visible != null && visible) {
        visibleAction(this)
        VISIBLE
    } else GONE
}