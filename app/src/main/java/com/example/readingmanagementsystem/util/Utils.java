package com.example.readingmanagementsystem.util;

import com.example.readingmanagementsystem.model.Book;

import java.util.ArrayList;

public class Utils {

    private static Utils INSTANCE;
    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> alreadyReadBooks;
    private static ArrayList<Book> wantToReadBooks;
    private static ArrayList<Book> currentlyReadingBooks;
    private static ArrayList<Book> favoriteBooks;
    private Utils() {
        if(allBooks == null) {
            allBooks = new ArrayList<>();
            initData();
        }
        if(alreadyReadBooks == null) {
            alreadyReadBooks = new ArrayList<>();
        }
        if(wantToReadBooks == null) {
            wantToReadBooks = new ArrayList<>();
        }
        if(currentlyReadingBooks == null) {
            currentlyReadingBooks = new ArrayList<>();
        }
        if(favoriteBooks == null) {
            favoriteBooks = new ArrayList<>();
        }
    }

    private void initData() {
        String longDescBook1 = "Atomic Habits by James Clear is a comprehensive, practical guide on how" +
                " to change your habits and get 1% better every day. Using a framework called the" +
                " Four Laws of Behavior Change, Atomic Habits teaches readers a simple set of rules" +
                " for creating good habits and breaking bad ones. Read the full summary to glean 3" +
                " key lessons from Atomic Habits, learn how to build a habit in 4 simple steps, and" +
                " get a handy reference guide for the strategies recommended throughout the book.";

        allBooks.add(new Book(1, "Atomic Habit", "James Clear", 535,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlkk-L1KiG7ca0hK9_uEefpx3f5KhI2E4h7w&usqp=CAU",
                "Short desc1", longDescBook1));
        allBooks.add(new Book(2, "Power Of Now", "Eckhart Tolle", 426,
                "https://upload.wikimedia.org/wikipedia/en/6/66/TPON_Cover_LG.jpg",
                "Short desc2", "Long desc2"));
        allBooks.add(new Book(3, "Rich Dad Poor Dad", "Robert Kiyosaki", 428,
                "https://play-lh.googleusercontent.com/s9MlpJJAxANSzuTZXcgXjeibG5Yqx9MOY0AXmbFQK-nLlCeBk_aXGjGWFPRatWM-eVI"
                , "Short desc3", "Long desc3"));
        allBooks.add(new Book(4, "How To Develop a Superpower Memory", "Harry Lorayne", 400,
                "https://images-na.ssl-images-amazon.com/images/I/51ONCz9jl-L.jpg",
                "Short desc4", "Long desc4"));
    }
    public static synchronized Utils getInstance() {
        return INSTANCE == null ? INSTANCE = new Utils() : INSTANCE;
    }


    public ArrayList<Book> getAllBooks() {
        return allBooks;
    }

    public void setAllBooks(ArrayList<Book> allBooks) {
        Utils.allBooks = allBooks;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        return alreadyReadBooks;
    }

    public void setAlreadyReadBooks(ArrayList<Book> alreadyReadBooks) {
        Utils.alreadyReadBooks = alreadyReadBooks;
    }

    public ArrayList<Book> getWantToReadBooks() {
        return wantToReadBooks;
    }

    public void setWantToReadBooks(ArrayList<Book> wantToReadBooks) {
        Utils.wantToReadBooks = wantToReadBooks;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        return currentlyReadingBooks;
    }

    public void setCurrentlyReadingBooks(ArrayList<Book> currentlyReadingBooks) {
        Utils.currentlyReadingBooks = currentlyReadingBooks;
    }

    public ArrayList<Book> getFavoriteBooks() {
        return favoriteBooks;
    }

    public void setFavoriteBooks(ArrayList<Book> favoriteBooks) {
        Utils.favoriteBooks = favoriteBooks;
    }

    public boolean addToAlreadyReadBooks(Book book) {
        try {
            alreadyReadBooks.add(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addToCurrentlyReadingBooks(Book book) {
        try {
            currentlyReadingBooks.add(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addToWantToReadBooks(Book book) {
        try {
            wantToReadBooks.add(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean addToFavoriteBooks(Book book) {
        try {
            favoriteBooks.add(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    public boolean deleteAlreadyReadBook(Book book) {
        try {
            alreadyReadBooks.remove(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteWantToReadBook(Book book) {
        try {
            wantToReadBooks.remove(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteCurrentlyReadingBook(Book book) {
        try {
            currentlyReadingBooks.remove(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteFavoriteBook(Book book) {
        try {
            favoriteBooks.remove(book);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
