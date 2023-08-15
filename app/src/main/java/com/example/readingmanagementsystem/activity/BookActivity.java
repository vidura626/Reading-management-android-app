package com.example.readingmanagementsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.model.Book;
import com.example.readingmanagementsystem.util.Utils;

import java.util.concurrent.atomic.AtomicBoolean;

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

//        get Data from Intent

        Intent intent = getIntent();
        if (intent != null) {
            Book book = (Book) intent.getSerializableExtra("book");
            setData(book);
            isAlreadyReadBook(book);
        }
    }

    /**
     * Enable or disable button
     * Add the book to already read arraylist
     * @param book
     */
    private void isAlreadyReadBook(Book book) {
        AtomicBoolean isRead = new AtomicBoolean(false);
        Utils.getInstance().getAlreadyReadBooks().forEach(alreadyReadBook -> {
            if (book.getId() == alreadyReadBook.getId()) {
                isRead.set(true);
            }
        });

        if (isRead.get()) {
            btnAddToAlreadyRead.setEnabled(false);
        } else {
            btnAddToAlreadyRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance().addToAlreadyReadBooks(book)) {
                        Toast.makeText(BookActivity.this, "Book added to already read", Toast.LENGTH_SHORT).show();
                        btnAddToAlreadyRead.setEnabled(false);
//                        TODO Navigate the user
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
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