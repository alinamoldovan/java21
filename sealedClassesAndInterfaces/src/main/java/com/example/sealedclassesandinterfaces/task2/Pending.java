package com.example.sealedclassesandinterfaces.task2;

public record Pending() implements TaskState{
    public Pending() {
        System.out.println("Task is not started");
    }
}
