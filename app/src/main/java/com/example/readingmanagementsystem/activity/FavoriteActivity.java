package com.example.readingmanagementsystem.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.adapters.BookRecViewAdapter;
import com.example.readingmanagementsystem.util.Utils;

public class FavoriteActivity extends AppCompatActivity {
    private BookRecViewAdapter adapter;
    private RecyclerView booksRecycleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        booksRecycleView = findViewById(R.id.booksRecycleView);
        adapter = new BookRecViewAdapter(this, ParentActivity.FAVORITE);
        adapter.setBooks(Utils.getInstance(this).getFavoriteBooks());
        booksRecycleView.setAdapter(adapter);
        booksRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}