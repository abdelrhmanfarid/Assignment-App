package com.free.assignmentapplication.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var params = mutableMapOf<String, Any>()
    var errorDialog: MutableLiveData<String> = MutableLiveData()
    var successDialog: MutableLiveData<String> = MutableLiveData()

    abstract fun stop()
    abstract fun start()


    fun showAlertDialog(message: String, context: Context) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Alert")
        builder.setMessage(message)
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()

    }
}
