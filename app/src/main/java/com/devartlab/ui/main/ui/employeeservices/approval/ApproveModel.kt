package com.devartlab.ui.main.ui.employeeservices.approval

data class ApproveModel(


   var sheet: String?,
   var action: String?,
   var date: String,
   var id: String,
   var name: String,
   var typeRequest: String?,
   var requestStartIn: String,
   var requestEndIn: String?,
   var notes: String,
   var managerId: String,
   var status: String,
   var comment: String,
   var approvalDate: String,
   var code: String

)