package com.example.readingmanagementsystem.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.adapters.BookRecViewAdapter;
import com.example.readingmanagementsystem.model.Book;
import com.example.readingmanagementsystem.util.Utils;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecycleView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

//        overridePendingTransition(R.anim.slide_in, R.anim.slide_in);

        adapter = new BookRecViewAdapter(this, ParentActivity.ALLBOOKS);
        booksRecycleView = findViewById(R.id.booksRecycleView);
        ArrayList<Book> allBooks = Utils.getInstance(this).getAllBooks();
        adapter.setBooks(allBooks);
        booksRecycleView.setAdapter(adapter);

//        TODO change to grid layout manager
//        booksRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        booksRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

        /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }*/
/*    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_out, R.anim.slide_out);
    }*/
}