package com.first.employee

 data class Employe ( var status :String,
                      var data: ArrayList<EmployeeData>
 )

data class EmployeeData ( var id :String,
                          var employee_name : String,
                          var employee_salary : String,
                          var employee_age : String,
                          var profile_image : String?

)
