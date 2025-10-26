package com.example.sealedclassesandinterfaces;

import com.example.sealedclassesandinterfaces.task1.Circle;
import com.example.sealedclassesandinterfaces.task1.Shape;
import com.example.sealedclassesandinterfaces.task1.ShapeOperations;
import com.example.sealedclassesandinterfaces.task2.InProgress;
import com.example.sealedclassesandinterfaces.task2.TaskOperations;
import com.example.sealedclassesandinterfaces.task2.TaskState;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SealedClassesAndInterfacesApplication {

    static void main(String[] args) {
        //SpringApplication.run(SealedClassesAndInterfacesApplication.class, args);

        Shape shape = new Circle(10);
        ShapeOperations shapeOperations = new ShapeOperations();

        Double permiter = shapeOperations.perimeter(shape);
        System.out.println("permiter = " + permiter);

        TaskState taskState = new InProgress(40);

        TaskOperations taskOperations = new TaskOperations();

        taskOperations.describe(taskState);
    }

}
