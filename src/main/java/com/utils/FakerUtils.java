package com.utils;

import com.github.javafaker.Faker;

public class FakerUtils {
    String authorName;
    String bookTitle;

    public String generateBookName(){
        Faker faker = new Faker();
//        return faker.regexify("[A-Za-z0-9 ,_-]{30}");
        bookTitle = faker.book().title();
        return bookTitle;
    }

    public String generateAuthorName(){
        Faker faker = new Faker();
        authorName = faker.book().author();
        return authorName;
    }

}
