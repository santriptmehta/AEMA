package com.blankspace.aema.Models

data class Plumbing(val id: String = "",
                    val name: String = "",
                    val email: String = "",
                    val roll_no: String = "",
                    val hostel_no : String = "",
                    val location : String = "",
                    val defect : String = "",
                    val admin_review_data : String = "",
                    val admin_resolved_checkbox : Boolean = false,
                    val dateTime : String = "",
                    val description : String = "")
