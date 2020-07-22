package com.first.employee.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.first.employee.EmployeeData
import com.first.employee.R
import com.first.employee.retrofitservice.inflate
import kotlinx.android.synthetic.main.item_employee.view.*

class EmployeeAdapter (var list : ArrayList<EmployeeData>)  : RecyclerView.Adapter<EmployeeAdapter.EmployeeHolder>(),Filterable {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeHolder {
        return EmployeeHolder(parent.inflate(R.layout.item_employee))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: EmployeeHolder, position: Int) {
        holder.bindHolder(list[position])

    }

  override  fun getFilter(): Filter {
        var filter = object :Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                var filterResults = FilterResults()
                if(charSequence == null || charSequence.length == 0){
                    filterResults.count = list.size
                    filterResults.values = list
                }
                else{
                    val searchChr = charSequence.toString().toLowerCase()
                    val products = ArrayList<EmployeeData>()
                    for (userModel in list) {
                        if (userModel.employee_name.toLowerCase().contains(searchChr)) {
                            products.add(userModel)
                        }
                    }
                    filterResults.count = products.size
                    filterResults.values = products
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                if (results!!.count > 0) {
                    list = results.values as ArrayList<EmployeeData>
                    notifyDataSetChanged()
                }

            }

        }
        return filter
    }

    inner class EmployeeHolder(itemView: View): RecyclerView.ViewHolder(itemView){



        fun bindHolder(data : EmployeeData){

            itemView.employee_name.text  = data.employee_name
            itemView.salary.text = data.employee_salary
            itemView.age.text = data.employee_age

            if (data.profile_image!!.isNotEmpty()) {
                Glide.with(itemView.context)
                    .applyDefaultRequestOptions(RequestOptions().error(R.mipmap.ic_launcher))
                    .load(data?.profile_image)
                    .into(itemView.image)

            }
        }



        }





}