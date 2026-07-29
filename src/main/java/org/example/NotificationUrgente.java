package org.example;

public sealed interface NotificationUrgente extends Notification permits NotificationSms, NotificationAppel {}
