package com.example.readingmanagementsystem.model;

import java.io.Serializable;

public class Book implements Serializable {
    private int id;
    private String name;
    private String author;
    private int pages;
    private String imageUrl;
    private String shortDesc;
    private String longDesc;

    private boolean isExpanded;
    private boolean isFavorite;
    private boolean isAlreadyRead;
    private boolean isCurrentlyReading;
    private boolean isWantToRead;

    public Book() {
    }

    public Book(int id, String name, String author, int pages, String imageUrl, String shortDesc, String longDesc) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.isExpanded = false;
        this.isFavorite = false;
        this.isAlreadyRead = false;
        this.isCurrentlyReading = false;
        this.isWantToRead = false;
    }

    public Book(String name, String author, int pages, String imageUrl, String shortDesc, String longDesc) {
        this.name = name;
        this.author = author;
        this.pages = pages;
        this.imageUrl = imageUrl;
        this.shortDesc = shortDesc;
        this.longDesc = longDesc;
        this.isExpanded = false;
        this.isFavorite = false;
        this.isAlreadyRead = false;
        this.isCurrentlyReading = false;
        this.isWantToRead = false;

    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isAlreadyRead() {
        return isAlreadyRead;
    }

    public void setAlreadyRead(boolean alreadyRead) {
        isAlreadyRead = alreadyRead;
        isCurrentlyReading = false;
        isWantToRead = false;
    }

    public boolean isCurrentlyReading() {
        return isCurrentlyReading;
    }

    public void setCurrentlyReading(boolean currentlyReading) {
        isCurrentlyReading = currentlyReading;
        isWantToRead = false;
        isAlreadyRead = false;
    }

    public boolean isWantToRead() {
        return isWantToRead;
    }

    public void setWantToRead(boolean wantToRead) {
        isWantToRead = wantToRead;
        isCurrentlyReading = false;
        isAlreadyRead = false;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getShortDesc() {
        return shortDesc;
    }

    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", pages=" + pages +
                ", imageUrl='" + imageUrl + '\'' +
                ", shortDesc='" + shortDesc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                '}';
    }
}
