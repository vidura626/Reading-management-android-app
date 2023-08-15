package com.example.readingmanagementsystem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.adapters.BookRecViewAdapter;
import com.example.readingmanagementsystem.util.Utils;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecycleView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        adapter = new BookRecViewAdapter(this, ParentActivity.ALLBOOKS);
        booksRecycleView = findViewById(R.id.booksRecycleView);

        adapter.setBooks(Utils.getInstance().getAllBooks());
        booksRecycleView.setAdapter(adapter);

//        TODO change to grid layout manager
//        booksRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        booksRecycleView.setLayoutManager(new LinearLayoutManager(this));

    }
}