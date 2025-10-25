package com.example.records;

public record Product(String name, int price) {
    public Product{
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }

    public Product discount(int discount) {
        if (discount < 0) {
            throw new IllegalArgumentException("Discount cannot be negative");
        }

        return new Product(name,price-discount);
    }
}
