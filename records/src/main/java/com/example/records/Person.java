package com.example.records;

public record Person(String email,String firstName,String lastName) {
    public Person{
        if (!(email.contains("@"))){
            throw new IllegalArgumentException("Invalid email address");
        }
    }
    public String toString(){
        return email+" "+firstName+" "+lastName;
    }
}
