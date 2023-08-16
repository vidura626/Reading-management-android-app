package com.example.readingmanagementsystem.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.readingmanagementsystem.model.Book;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Utils {

    public final static String ALL_BOOKS = "all_books";
    public final static String ALREADY_READ_BOOKS = "already_read_books";
    public final static String WANT_TO_READ_BOOKS = "want_to_read_books";
    public final static String CURRENTLY_READING_BOOKS = "currently_reading_books";
    public final static String FAVORITE_BOOKS = "favorite_books";
    public final static String SHPREF = "alternative_db";
    private Gson gson;
    private SharedPreferences sharedPreferences;
    private static Utils INSTANCE;
    private static ArrayList<Book> allBooks;
    private static ArrayList<Book> alreadyReadBooks;
    private static ArrayList<Book> wantToReadBooks;
    private static ArrayList<Book> currentlyReadingBooks;
    private static ArrayList<Book> favoriteBooks;
    private DeleteCallback deleteCallback;
    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences(SHPREF, Context.MODE_PRIVATE);

        gson = new Gson();
        if (getAllBooks() == null) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (getAlreadyReadBooks() == null) {
            editor.putString(ALREADY_READ_BOOKS, gson.toJson(getAlreadyReadBooks()));
            editor.commit();
        }
        if (getWantToReadBooks() == null) {
            editor.putString(WANT_TO_READ_BOOKS, gson.toJson(getWantToReadBooks()));
            editor.commit();
        }
        if (getCurrentlyReadingBooks() == null) {
            editor.putString(CURRENTLY_READING_BOOKS, gson.toJson(getCurrentlyReadingBooks()));
            editor.commit();
        }
        if (getFavoriteBooks() == null) {
            editor.putString(FAVORITE_BOOKS, gson.toJson(getFavoriteBooks()));
            editor.commit();
        }
    }

    private void initData() {
        ArrayList<Book> books = new ArrayList<>();

        String longDescBook1 = "Atomic Habits by James Clear is a comprehensive, practical guide on how" +
                " to change your habits and get 1% better every day. Using a framework called the" +
                " Four Laws of Behavior Change, Atomic Habits teaches readers a simple set of rules" +
                " for creating good habits and breaking bad ones. Read the full summary to glean 3" +
                " key lessons from Atomic Habits, learn how to build a habit in 4 simple steps, and" +
                " get a handy reference guide for the strategies recommended throughout the book.";

        books.add(new Book(1, "Atomic Habit", "James Clear", 535,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlkk-L1KiG7ca0hK9_uEefpx3f5KhI2E4h7w&usqp=CAU",
                "Short desc1", longDescBook1));
        books.add(new Book(2, "Power Of Now", "Eckhart Tolle", 426,
                "https://upload.wikimedia.org/wikipedia/en/6/66/TPON_Cover_LG.jpg",
                "Short desc2", "Long desc2"));
        books.add(new Book(3, "Rich Dad Poor Dad", "Robert Kiyosaki", 428,
                "https://play-lh.googleusercontent.com/s9MlpJJAxANSzuTZXcgXjeibG5Yqx9MOY0AXmbFQK-nLlCeBk_aXGjGWFPRatWM-eVI"
                , "Short desc3", "Long desc3"));
        books.add(new Book(4, "How To Develop a Superpower Memory", "Harry Lorayne", 400,
                "https://images-na.ssl-images-amazon.com/images/I/51ONCz9jl-L.jpg",
                "Short desc4", "Long desc4"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_BOOKS, gson.toJson(books));
        editor.commit();
    }

    public static synchronized Utils getInstance(Context context) {
        return INSTANCE == null ? INSTANCE = new Utils(context) : INSTANCE;
    }


    public ArrayList<Book> getAllBooks() {
        ArrayList<Book> books = (ArrayList<Book>) gson.fromJson(sharedPreferences.getString(ALL_BOOKS, null),
                new TypeToken<ArrayList<Book>>() {
                });
        return books;
    }

    public ArrayList<Book> getAlreadyReadBooks() {
        ArrayList<Book> books = (ArrayList<Book>) gson.fromJson(sharedPreferences.getString(ALREADY_READ_BOOKS, null),
                new TypeToken<ArrayList<Book>>() {
                });
        if (books == null) {
            return new ArrayList<Book>();
        }
        books.forEach(book -> book.setAlreadyRead(true));
        return books;
    }

    public ArrayList<Book> getWantToReadBooks() {
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(WANT_TO_READ_BOOKS, null),
                new TypeToken<ArrayList<Book>>() {
                });
        if(books == null) {
            return new ArrayList<Book>();
        }
        books.forEach(book -> book.setWantToRead(true));
        return books;
    }

    public ArrayList<Book> getCurrentlyReadingBooks() {
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(CURRENTLY_READING_BOOKS, null),
                new TypeToken<ArrayList<Book>>() {
                });
        if(books == null) {
            return new ArrayList<Book>();
        }
        books.forEach(book -> book.setCurrentlyReading(true));
        return books;
    }

    public ArrayList<Book> getFavoriteBooks() {
        ArrayList<Book> books = gson.fromJson(sharedPreferences.getString(FAVORITE_BOOKS, null),
                new TypeToken<ArrayList<Book>>() {
                });
        if (books == null) {
            return new ArrayList<Book>();
        }
        books.forEach(book -> book.setFavorite(true));
        System.out.println("SharedPreferences: " + books);
        return books;
    }

    public boolean addToAlreadyReadBooks(Book book) {
        ArrayList<Book> list = getAlreadyReadBooks();
        book.setAlreadyRead(true);
        if (list.add(book)) {
            sharedPreferences.edit()
                    .remove(ALREADY_READ_BOOKS)
                    .putString(ALREADY_READ_BOOKS, gson.toJson(list))
                    .commit();
            return true;
        }
        return false;
    }

    public boolean addToCurrentlyReadingBooks(Book book) {
        ArrayList<Book> list = getCurrentlyReadingBooks();
        book.setCurrentlyReading(true);
        if (list.add(book)) {
            sharedPreferences.edit()
                    .remove(CURRENTLY_READING_BOOKS)
                    .putString(CURRENTLY_READING_BOOKS, gson.toJson(list))
                    .commit();
            return true;
        }
        return false;
    }

    public boolean addToWantToReadBooks(Book book) {
        ArrayList<Book> list = getWantToReadBooks();
        book.setWantToRead(true);
        if (list.add(book)) {
            sharedPreferences.edit()
                    .remove(WANT_TO_READ_BOOKS)
                    .putString(WANT_TO_READ_BOOKS, gson.toJson(list))
                    .commit();
            return true;
        }
        return false;
    }

    public boolean addToFavoriteBooks(Book book) {
        ArrayList<Book> list = getFavoriteBooks();
        book.setFavorite(true);
        if (list.add(book)) {
            sharedPreferences.edit()
                    .remove(FAVORITE_BOOKS)
                    .putString(FAVORITE_BOOKS, gson.toJson(list))
                    .commit();
            return true;
        }
        return false;
    }


    public boolean deleteAlreadyReadBook(Book book) {
        ArrayList<Book> list = getAlreadyReadBooks();
        ArrayList<Book> collect = list.stream().filter(b -> b.getId() != book.getId()).collect(Collectors.toCollection(ArrayList::new));
        sharedPreferences.edit()
                .remove(ALREADY_READ_BOOKS)
                .putString(ALREADY_READ_BOOKS, gson.toJson(collect))
                .commit();
        return true;
    }

    public boolean deleteWantToReadBook(Book book) {
        ArrayList<Book> list = getWantToReadBooks();
        ArrayList<Book> collect = list.stream().filter(b -> b.getId() != book.getId()).collect(Collectors.toCollection(ArrayList::new));
        sharedPreferences.edit()
                .remove(WANT_TO_READ_BOOKS)
                .putString(WANT_TO_READ_BOOKS, gson.toJson(collect))
                .commit();
        return true;
    }

    public boolean deleteCurrentlyReadingBook(Book book) {
        ArrayList<Book> list = getCurrentlyReadingBooks();
        ArrayList<Book> collect = list.stream().filter(b -> b.getId() != book.getId()).collect(Collectors.toCollection(ArrayList::new));
        sharedPreferences.edit()
                .remove(CURRENTLY_READING_BOOKS)
                .putString(CURRENTLY_READING_BOOKS, gson.toJson(collect))
                .commit();
        return true;

    }

    public void setDeleteCallback(DeleteCallback deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    public void deleteFavoriteBook(Book book) {
        ArrayList<Book> list = getFavoriteBooks();
        ArrayList<Book> collect = list.stream().filter(b -> b.getId() != book.getId()).collect(Collectors.toCollection(ArrayList::new));
        sharedPreferences.edit()
                .remove(FAVORITE_BOOKS)
                .putString(FAVORITE_BOOKS, gson.toJson(collect))
                .commit();
        deleteCallback.onDelete(FAVORITE_BOOKS.toLowerCase(),true);

    }

    public interface DeleteCallback {
        void onDelete(String id, boolean isSuccess);
    }
}
