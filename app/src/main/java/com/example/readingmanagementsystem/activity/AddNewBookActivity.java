package com.example.readingmanagementsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.model.Book;
import com.example.readingmanagementsystem.util.Utils;

public class AddNewBookActivity extends AppCompatActivity implements View.OnFocusChangeListener {

    private EditText txtNewBook;
    private EditText txtNewAuthor;
    private EditText txtPages;
    private EditText txtShortDescription;
    private EditText txtLongDescription;
    private EditText txtImageUrl;
    private TextView txtNewBookId;
    private ImageView imageViewBook;
    private Button btnChooseImage;
    private Button btnSubmit;
    private Button btnClear;

    private boolean IS_SAVE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_book);
        IS_SAVE = true;
        initViews();

        txtNewBookId.setText(String.valueOf(Utils.getInstance(this).getAllBooks().size() + 1));

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("book")) {
            Book book = (Book) intent.getSerializableExtra("book");
            setData(book);
            IS_SAVE = false;
        }

        btnChooseImage.setOnClickListener(v -> {
            if (txtImageUrl.getText().toString().isEmpty()) {
                txtImageUrl.setError("Image URL is required");
            } else {
                txtImageUrl.setError(null);
                Glide.with(this).asBitmap().load(txtImageUrl.getText().toString()).into(imageViewBook);
            }
        });

        btnSubmit.setOnClickListener(v -> {
            if (IS_SAVE) {
                if (formValid()) {
                    Book book = new Book(
                            txtNewBook.getText().toString(),
                            txtNewAuthor.getText().toString(),
                            Integer.parseInt(txtPages.getText().toString()),
                            txtImageUrl.getText().toString(),
                            txtShortDescription.getText().toString(),
                            txtLongDescription.getText().toString()
                    );
                    int i = Utils.getInstance(this).addNewBook(book);
                    if (i > 0) {
                        Toast.makeText(this, "Book added successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(this, AllBooksActivity.class));
                    } else {
                        Toast.makeText(this, "Failed to add book", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (formValid()) {
                    Book book = new Book(
                            Integer.parseInt(txtNewBookId.getText().toString()),
                            txtNewBook.getText().toString(),
                            txtNewAuthor.getText().toString(),
                            Integer.parseInt(txtPages.getText().toString()),
                            txtImageUrl.getText().toString(),
                            txtShortDescription.getText().toString(),
                            txtLongDescription.getText().toString()
                    );

                    new AlertDialog.Builder(this)
                            .setMessage("Are you sure you want to update this book?")
                            .setPositiveButton("Yes", ((dialog, which) -> {
                                if (Utils.getInstance(this).updateBook(book)) {
                                    Toast.makeText(this, "Book updated", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(this, AllBooksActivity.class));
                                } else {
                                    Toast.makeText(this, "Failed to update book", Toast.LENGTH_SHORT).show();
                                }
                            }))
                            .setNegativeButton("No", ((dialog, which) -> {
                                dialog.cancel();
                            }))
                            .create()
                            .show();
                }
            }
        });

        btnClear.setOnClickListener(v -> {
            clearForm();
        });
    }

    private void setData(Book book) {
        txtNewBookId.setText(String.valueOf(book.getId()));
        txtNewBook.setText(book.getName());
        txtNewAuthor.setText(book.getAuthor());
        txtPages.setText(String.valueOf(book.getPages()));
        txtImageUrl.setText(book.getImageUrl());
        txtShortDescription.setText(book.getShortDesc());
        txtLongDescription.setText(book.getLongDesc());
        Glide.with(this).asBitmap().load(book.getImageUrl()).into(imageViewBook);
    }

    private void initViews() {
        txtNewBookId = findViewById(R.id.txtNewBookId);
        txtNewBook = findViewById(R.id.txtNewBook);
        txtNewAuthor = findViewById(R.id.txtNewAuthor);
        txtImageUrl = findViewById(R.id.txtxImgeURL);
        txtPages = findViewById(R.id.txtPages);
        txtShortDescription = findViewById(R.id.txtShortDescription);
        txtLongDescription = findViewById(R.id.txtLongDescription);
        imageViewBook = findViewById(R.id.imgNewBook);
        btnChooseImage = findViewById(R.id.btnShowImg);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnClear = findViewById(R.id.btnClear);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        formValid();
    }


    void clearForm() {
        txtNewBook.setText("");
        txtNewAuthor.setText("");
        txtPages.setText("");
        txtShortDescription.setText("");
        txtLongDescription.setText("");
        txtImageUrl.setText("");
        txtNewBookId.setText(Utils.getInstance(this).getAllBooks().size() + 1);
        Glide.with(this).clear(imageViewBook);
    }

    private boolean formValid() {
        if (txtNewBook.getText().toString().isEmpty()) {
            txtNewBook.setError("Book Name is required");
            txtNewBook.requestFocus();
            return false;
        } else {
            txtNewBook.setError(null);
        }
        if (txtNewAuthor.getText().toString().isEmpty()) {
            txtNewAuthor.setError("Author Name is required");
            txtNewAuthor.requestFocus();
            return false;
        } else {
            txtNewAuthor.setError(null);
        }
        if (txtPages.getText().toString().isEmpty()) {
            txtPages.setError("Pages is required");
            txtPages.requestFocus();
            return false;
        } else {
            txtPages.setError(null);
        }
        if (txtShortDescription.getText().toString().isEmpty()) {
            txtShortDescription.setError("Short Description is required");
            txtShortDescription.requestFocus();
            return false;
        } else {
            txtShortDescription.setError(null);
        }
        if (txtLongDescription.getText().toString().isEmpty()) {
            txtLongDescription.setError("Long Description is required");
            txtLongDescription.requestFocus();
            return false;
        } else {
            txtLongDescription.setError(null);
        }
        if (txtImageUrl.getText().toString().isEmpty()) {
            txtImageUrl.setError("Image URL is required");
            txtImageUrl.requestFocus();
            return false;
        } else {
            txtImageUrl.setError(null);
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}