package com.example.sealedclassesandinterfaces;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SealedClassesAndInterfacesApplication {

    static void main(String[] args) {
        //SpringApplication.run(SealedClassesAndInterfacesApplication.class, args);

        Shape shape = new Circle(10);
        ShapeOperations shapeOperations = new ShapeOperations();

        Double permiter = shapeOperations.perimeter(shape);
        System.out.println("permiter = " + permiter);
    }

}
