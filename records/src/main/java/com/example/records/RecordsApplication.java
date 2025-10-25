package com.example.records;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class RecordsApplication {

    static void main(String[] args) {
        Person person = new Person("alina@gmail.com","alina","alina");
        System.out.println(person);
        //SpringApplication.run(RecordsApplication.class, args);

//        Person personError = new Person("a","a","a");
//        System.out.println(personError);

        Product productOk = new Product("produs",10);
        System.out.println(productOk);

//        Product productFail = new Product("produs",-2);
//        System.out.println(productFail);
        Product productDiscount = productOk.discount(5);
        System.out.println(productDiscount);
        System.out.println("Filtered products");
        List<Product> products = List.of(
                new Product("Pen", 2),
                new Product("Laptop", 1500),
                new Product("Book", 25)
        );

        products.stream()
                .filter(product -> product.price()>2)
                .forEach(System.out::println);
    }

}
