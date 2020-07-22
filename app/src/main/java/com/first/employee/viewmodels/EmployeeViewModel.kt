package com.first.employee.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vajro.FailureHandler
import com.first.employee.Employe
import com.first.employee.EmployeeData
import com.first.employee.retrofitservice.ApiInterface
import com.first.employee.retrofitservice.client
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Response


class EmployeeViewModel(application: Application)  : AndroidViewModel(application) {

    private val disposable = CompositeDisposable()
     var userLiveData: MutableLiveData<ArrayList<EmployeeData>> = MutableLiveData()
    var userArrayList: ArrayList<EmployeeData>? = null

    fun getEmp(){
        fun observer(): DisposableSingleObserver<Employe> {
            return object : DisposableSingleObserver<Employe>() {
                override fun onSuccess(t: Employe) {
                    userLiveData!!.postValue(t.data)
                    var doll = Gson().toJson(t.data)
                    Log.d("----------------res",doll)
                }

                override fun onError(e: Throwable) {
                    FailureHandler.processFailure(getApplication() , e)
                }

            }
        }

        disposable.add(
            client.apiInterface.getEmployee()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer())
        )

    }

    fun getUserMutableLiveData(): LiveData<ArrayList<EmployeeData>> {

        getEmp()
        var doll = Gson().toJson(userLiveData)
        Log.d("----------------model",doll)
        return userLiveData
    }



}