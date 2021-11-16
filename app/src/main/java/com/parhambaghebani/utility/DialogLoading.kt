package com.parhambaghebani.utility

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.parhambaghebani.R

class DialogLoading : DialogFragment() {

    companion object {
        const val Tag = "DialogLoading"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loading_dialog, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.run {
            val width = ViewGroup.LayoutParams.MATCH_PARENT
            val height = ViewGroup.LayoutParams.MATCH_PARENT
            setLayout(width, height)
            setBackgroundDrawable(ColorDrawable(Color.parseColor("#80000000")))
        }
    }

    fun isShowing(): Boolean {
        return dialog != null && dialog!!.isShowing && !isRemoving
    }
}