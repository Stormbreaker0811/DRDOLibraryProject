package com.example.drdolibraryproject.gettersetter;

import java.io.Serializable;

public class Library implements Serializable {
    private String bookName,authorName,category,publishYear;
    public Library(String bookName,String authorName,String category,String publishYear){
        this.bookName = bookName;
        this.authorName = authorName;
        this.category = category;
        this.publishYear = publishYear;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }
}
