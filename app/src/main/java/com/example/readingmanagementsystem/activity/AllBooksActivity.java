package com.example.readingmanagementsystem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.adapters.BookRecViewAdapter;
import com.example.readingmanagementsystem.model.Book;

import java.util.ArrayList;

public class AllBooksActivity extends AppCompatActivity {

    private RecyclerView booksRecycleView;
    private BookRecViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_books);

        adapter = new BookRecViewAdapter(this);
        booksRecycleView = findViewById(R.id.booksRecycleView);


        ArrayList<Book> books = new ArrayList<>();
        books.add(new Book(1, "Atomic Habit", "James Clear", 535,
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQlkk-L1KiG7ca0hK9_uEefpx3f5KhI2E4h7w&usqp=CAU",
                "Short desc1", "Long desc1"));
        books.add(new Book(2, "Power Of Now", "Eckhart Tolle", 426,
                "https://upload.wikimedia.org/wikipedia/en/6/66/TPON_Cover_LG.jpg",
                "Short desc2", "Long desc2"));
        books.add(new Book(3, "Rich Dad Poor Dad", "Robert Kiyosaki", 428,
                "https://play-lh.googleusercontent.com/s9MlpJJAxANSzuTZXcgXjeibG5Yqx9MOY0AXmbFQK-nLlCeBk_aXGjGWFPRatWM-eVI"
                , "Short desc3", "Long desc3"));
        books.add(new Book(4, "How To Develop a Superpower Memory", "Harry Lorayne", 400,
                "https://images-na.ssl-images-amazon.com/images/I/51ONCz9jl-L.jpg",
                "Short desc4", "Long desc4"));
        adapter.setBooks(books);
        booksRecycleView.setAdapter(adapter);
//        booksRecycleView.setLayoutManager(new GridLayoutManager(this, 2));
        booksRecycleView.setLayoutManager(new LinearLayoutManager(this));





    }
}