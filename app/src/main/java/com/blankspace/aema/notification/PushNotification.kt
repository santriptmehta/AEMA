package com.blankspace.aema.notification

data class PushNotification(
    val data: NotificationData,
    val to: String
)