package com.example.sealedclassesandinterfaces.task2;

import org.springframework.boot.SpringApplication;

public class TaskOperations {
    public void describe(TaskState state) {
        switch (state){
            case Pending pending -> System.out.println("Pending...");
            case InProgress inProgress -> System.out.println(String.format("In Progress...:%s" + "%%", inProgress.progress()));
            case Completed completed -> System.out.println(String.format("Completed...:%s", completed.message()));
        }
    }
}
