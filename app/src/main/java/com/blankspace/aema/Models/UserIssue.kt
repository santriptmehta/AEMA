package com.blankspace.aema.Models

class UserIssue (
    val id_issue: String = "",
    val userRollno : String = "",
    val userEmail : String = "",
    val userHostelno : String = "",
    val issueLocation : String = "",
    val issueDefect : String = "",
    val issueDescribe : String = "",
    val issueDataTime : String = "",
    val admin_review_data : String = "",
    val issueType : String = "",
    val admin_resolved_checkbox : Boolean = false
        )