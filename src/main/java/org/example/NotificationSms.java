package org.example;

public record NotificationSms(String numero, String message) implements NotificationUrgente{}
