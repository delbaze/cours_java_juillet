package org.example;

public record NotificationStandard(String email, String message) implements Notification{}
