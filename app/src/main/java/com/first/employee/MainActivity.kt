package com.first.employee

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vajro.FailureHandler
import com.first.employee.adapter.EmployeeAdapter
import com.first.employee.retrofitservice.addDivider
import com.first.employee.retrofitservice.client
import com.first.employee.retrofitservice.setLayoutManager
import com.first.employee.retrofitservice.toast
import com.first.employee.viewmodels.EmployeeViewModel
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()
    private lateinit var employeeAdapter: EmployeeAdapter
    lateinit var viewModel: EmployeeViewModel
    var savedList = ArrayList<EmployeeData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(EmployeeViewModel::class.java)

        setAdapter()
        employee()
        callacks()
    }


    fun employee() {
        viewModel.getUserMutableLiveData().observe(this, Observer<ArrayList<EmployeeData>> {
            var sortList = it.sortedWith(compareBy({ it.employee_name }))
            sortList.forEach {
                savedList?.add(it)
            }
            setAdapter(savedList)

        })

    }

    private fun callacks() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                if (p0!!.isNotEmpty()) {
                    employeeAdapter.getFilter().filter(p0)
               }else{
                    employeeAdapter.list = savedList
                    employeeAdapter.notifyDataSetChanged()
                }

                return true
            }

        })


    }

    private fun setAdapter() {
        recyclerView_emp.setLayoutManager(this)
        recyclerView_emp.addDivider()
        employeeAdapter = EmployeeAdapter(ArrayList())
        recyclerView_emp.adapter = employeeAdapter

    }

    private fun setAdapter(currentList: ArrayList<EmployeeData>) {

        employeeAdapter.list = currentList
        employeeAdapter.notifyDataSetChanged()
    }

}