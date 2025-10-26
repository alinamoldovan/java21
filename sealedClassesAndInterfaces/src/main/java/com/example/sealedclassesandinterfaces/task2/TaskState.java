package com.example.sealedclassesandinterfaces.task2;

public sealed interface TaskState permits Pending, InProgress,Completed {
}
