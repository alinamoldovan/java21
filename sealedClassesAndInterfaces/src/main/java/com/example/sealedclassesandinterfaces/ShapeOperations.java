package com.example.sealedclassesandinterfaces;

public class ShapeOperations {

    public double perimeter(Shape shape) {
        return switch (shape) {
            case Circle c -> {
                System.out.println("circle");
                double v = 2 * Math.PI * c.radius();
                yield v;
            }
            case Rectangle r -> {
                System.out.println("rectangle");
                double v = 2 * (r.radius() + r.radius());
                yield v;
            }
            case Triangle t -> {
                System.out.println("triangle");
                double v = t.radius() + t.radius() + t.radius();
                yield v;
            }
        };
    }
}
