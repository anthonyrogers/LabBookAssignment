package com.example.anthonyrogers.lab7book;

public class Book {

    int id;
    String title;
    String author;
    int published;
    String coverURL;

    public Book(){}

    public Book(int id, String title, String author, int published, String coverURL){
        this.id = id;
        this.title = title;
        this.author = author;
        this.published = published;
        this.coverURL = coverURL;
    }


}
