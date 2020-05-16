package com.kibz.worldofplay_test.ui.callback

import android.view.View

abstract class Interfaces {

    interface OnRecyclerItemClick {
        fun onItemClick(v: View, listItem: Any)
    }

}