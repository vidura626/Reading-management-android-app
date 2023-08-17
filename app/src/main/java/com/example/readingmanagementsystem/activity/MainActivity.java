package com.example.readingmanagementsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.readingmanagementsystem.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAllBooks;
    private Button btnCurrentlyReading;
    private Button btnAlreadyRead;
    private Button btnWantedToRead;
    private Button btnFavorite;
    private Button btnAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        btnAllBooks.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AllBooksActivity.class);
            startActivity(intent);
        });

        btnAlreadyRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AlreadyReadActivity.class);
            startActivity(intent);
        });

        btnWantedToRead.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WantToReadActivity.class);
            startActivity(intent);
        });

        btnFavorite.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FavoriteActivity.class);
            startActivity(intent);
        });


        btnAbout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });


        btnCurrentlyReading.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CurrentlyReadingActivity.class);
            startActivity(intent);
        });

        btnAbout.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle(getString(R.string.app_name))
                    .setMessage("\nThis is a reading management application which can be helped people who interact with reading.\n\n" +
                            "You can visit creator of WINE Readings applications portfolio site by clicking Visit button.\n\n" +
                            "Thank you !")
                    .setPositiveButton("Visit", (dialog, which) -> {
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra("url", "https://vidura626.github.io/my_Profile/");
                        startActivity(intent);
                    })
                    .setNegativeButton("Dismiss", (dialog, which) -> dialog.cancel())
                    .create().show();
        });
    }

    /**
     * Initialize the views which from main_activity.xml
     */
    private void initViews() {
        btnAllBooks = findViewById(R.id.btnAllBooks);
        btnAlreadyRead = findViewById(R.id.btnAlreadyRead);
        btnWantedToRead = findViewById(R.id.btnWantToRead);
        btnCurrentlyReading = findViewById(R.id.btnCurrentlyReadeing);
        btnFavorite = findViewById(R.id.btnFavorite);
        btnAbout = findViewById(R.id.btnAbout);
    }

}