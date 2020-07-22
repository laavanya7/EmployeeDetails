package com.example.vajro

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.first.employee.R
import com.first.employee.retrofitservice.ErrorHandler
import com.first.employee.retrofitservice.NetworkUtil
import com.first.employee.retrofitservice.toast
import retrofit2.HttpException
import java.net.SocketException

object FailureHandler {
    fun processFailure(context: Context, t: Throwable?) {
        when {
            !NetworkUtil.isConnected(context) -> context.getString(R.string.error_connection).toast(context)

            t is HttpException -> {

                ErrorHandler.processError(context, t.response().code(), t.response().errorBody())

                Log.d("response code", t.response().code().toString())
                Log.d("response body", t.response().errorBody().toString())
            }
            t is SocketException -> {

                context.getString(R.string.error_server).toast(context)
            }
            else -> {
                Toast.makeText(context, context.getString(R.string.error_failure), Toast.LENGTH_SHORT).show()
                Log.d("error", t.toString())
            }

        }
    }
}