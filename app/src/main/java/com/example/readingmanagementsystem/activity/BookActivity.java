package com.example.readingmanagementsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.model.Book;

public class BookActivity extends AppCompatActivity {

    private TextView txtViewName;
    private TextView txtViewAuthor;
    private TextView txtViewLongDesc;
    private TextView txtViewPages;
    private ImageView imageViewBook;
    private Button btnAddToCurrentlyReading;
    private Button btnAddToWantToRead;
    private Button btnAddToAlreadyRead;
    private Button btnAddToFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        initViews();

//        TODO: get data from recyclerView

        String longDesc = "Atomic Habits by James Clear is a comprehensive, practical guide on how" +
                " to change your habits and get 1% better every day. Using a framework called the" +
                " Four Laws of Behavior Change, Atomic Habits teaches readers a simple set of rules" +
                " for creating good habits and breaking bad ones. Read the full summary to glean 3" +
                " key lessons from Atomic Habits, learn how to build a habit in 4 simple steps, and" +
                " get a handy reference guide for the strategies recommended throughout the book.";
        Book book = new Book(1, "Atomic Habit", "James Clear", 535,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlkk-L1KiG7ca0hK9_uEefpx3f5KhI2E4h7w&usqp=CAU",
                "Theory of Habits", longDesc);

        setData(book);
    }

    private void setData(Book book) {
        txtViewName.setText(book.getName());
        txtViewAuthor.setText(book.getAuthor());
        txtViewLongDesc.setText(book.getLongDesc());
        txtViewPages.setText(String.valueOf(book.getPages()));
        Glide.with(this).asBitmap().load(book.getImageUrl()).into(imageViewBook);
    }


    void initViews() {
        txtViewName = findViewById(R.id.txtViewName);
        txtViewAuthor = findViewById(R.id.txtViewAuthor);
        txtViewLongDesc = findViewById(R.id.txtViewLongDesc);
        txtViewPages = findViewById(R.id.txtViewPages);
        imageViewBook = findViewById(R.id.imgBook);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
    }

}