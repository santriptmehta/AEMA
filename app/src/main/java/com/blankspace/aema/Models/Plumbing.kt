package com.blankspace.aemaadmin.model

data class Plumbing(val id_issue: String = "",
                    val userRollno : String = "",
                    val name : String = "",
                    val userEmail : String = "",
                    val userHostelno : String = "",
                    val issueLocation : String = "",
                    val issueDefect : String = "",
                    val issueDescribe : String = "",
                    val issueDataTime : String = "",
                    val admin_review_data : String = "",
                    val issueType : String = "",
                    val admin_resolved_checkbox : Boolean = false)
