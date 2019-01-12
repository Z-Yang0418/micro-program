package com.company.program.resource.service;

import lombok.Data;

@Data
public class Book {

    private String name;

    private String author;

    private String price;

    public Book(String name, String author, String price) {
        this.name = name;
        this.author = author;
        this.price = price;
    }
}
