package com.blankspace.aema.Models

data class Maintainance(val id: String = "",
                        val name: String = "",
                        val email: String = "",
                        val roll_no: String = "",
                        val hostel_no : String = "",
                        val location : String = "",
                        val dateTime :  String = "",
                        val admin_review_data : String = "",
                        val admin_resolved_checkbox : Boolean = false,
                        val defect : String = "",
                        val description : String = "")