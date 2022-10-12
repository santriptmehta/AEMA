package com.blankspace.aema

data class PushNotification(
    val data: NotificationData,
    val to: String
)