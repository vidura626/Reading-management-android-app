package com.example.readingmanagementsystem.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.model.Book;
import com.example.readingmanagementsystem.util.Utils;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class BookActivity extends AppCompatActivity implements Utils.DeleteCallback {

    private TextView txtViewName;
    private TextView txtViewAuthor;
    private TextView txtViewLongDesc;
    private TextView txtViewPages;
    private ImageView imageViewBook;
    private Button btnAddToCurrentlyReading;
    private Button btnAddToWantToRead;
    private Button btnAddToAlreadyRead;
    private Button btnAddToFavorite;
    private Button btnDeleteBook;
    private Button btnUpdateBook;

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
            isCurrentlyReadingBook(book);
            isWantToReadBook(book);
            isFavoriteBook(book);
            deleteBook(book);
            updateBook(book);
        }
    }

    private void updateBook(Book book) {

        getValuesFromUI();
        btnUpdateBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BookActivity.this, AddNewBookActivity.class);
                intent.putExtra("book", (Serializable) book);
                startActivity(intent);
            }
        });
    }

    private void getValuesFromUI() {
        txtViewName.setText(txtViewName.getText());
        txtViewAuthor.setText(txtViewAuthor.getText());
        txtViewLongDesc.setText(txtViewLongDesc.getText());
        txtViewPages.setText(txtViewPages.getText());

    }

    private void deleteBook(Book book) {
        btnDeleteBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(BookActivity.this)
                        .setMessage("Are you sure you want to delete this book?")
                        .setPositiveButton("Yes", ((dialog, which) -> {
                            if (Utils.getInstance(BookActivity.this).deleteBook(book)) {
                                Toast.makeText(BookActivity.this, "Book deleted", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(BookActivity.this, AllBooksActivity.class));
                            }
                        }))
                        .setNegativeButton("No", ((dialog, which) -> {
                            dialog.cancel();
                        }))
                        .create()
                        .show();
            }
        });
    }

    /**
     * Enable or disable button based on book status
     * Add the book to favorite arraylist
     *
     * @param book
     */
    private void isFavoriteBook(Book book) {
        AtomicBoolean isFavorite = new AtomicBoolean(false);
        Utils.getInstance(this).getFavoriteBooks().forEach(favoriteBook -> {
            if (book.getId() == favoriteBook.getId()) {
                isFavorite.set(true);
            }
        });

        if (isFavorite.get()) {
            btnAddToFavorite.setEnabled(false);
        }else {
            btnAddToFavorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (Utils.getInstance(BookActivity.this).addToFavoriteBooks(book)) {
                        Toast.makeText(BookActivity.this, "Book added to favorite", Toast.LENGTH_SHORT).show();
                        btnAddToFavorite.setEnabled(false);
                        Intent intent = new Intent(BookActivity.this, FavoriteActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    /**
     * Enable or disable button based on book status
     * Add the book to want to read arraylist
     * @param book
     */
    private void isWantToReadBook(Book book) {
        AtomicBoolean isWantToRead = new AtomicBoolean(false);
        Utils.getInstance(this).getWantToReadBooks().forEach(wantToReadBook -> {
            if (book.getId() == wantToReadBook.getId()) {
                isWantToRead.set(true);
            }
        });

        if (isWantToRead.get()) {
            btnAddToWantToRead.setEnabled(false);
        } else {
            btnAddToWantToRead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (validateCarts(book, ParentActivity.WANTTOREAD)) {
                        if (Utils.getInstance(BookActivity.this).addToWantToReadBooks(book)) {
                            Toast.makeText(BookActivity.this, "Book added to want to read", Toast.LENGTH_SHORT).show();
                            btnAddToWantToRead.setEnabled(false);
                            Intent intent = new Intent(BookActivity.this, WantToReadActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        ParentActivity activity = setActivity(book);
                        System.out.println("Want to read fromActivity " + activity);
                        performActivity(
                                activity,
                                ParentActivity.WANTTOREAD,
                                book,
                                "want to read"
                        );
                    }


                }
            });
        }
    }
    /**
     * Enable or disable button based on book status
     * Add the book to currently reading arraylist
     * @param book
     */
    private void isCurrentlyReadingBook(Book book) {
        AtomicBoolean isReading = new AtomicBoolean(false);
        Utils.getInstance(this).getCurrentlyReadingBooks().forEach(currentlyReadingBook -> {
            if (book.getId() == currentlyReadingBook.getId()) {
                isReading.set(true);
            }
        });

        if (isReading.get()) {
            btnAddToCurrentlyReading.setEnabled(false);
        } else {
            btnAddToCurrentlyReading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean isValid = validateCarts(book, ParentActivity.CURRENTLYREADING);
                    if (isValid) {
                        if (Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book)) {
                            Toast.makeText(BookActivity.this, "Book added to currently reading", Toast.LENGTH_SHORT).show();
                            btnAddToCurrentlyReading.setEnabled(false);
                            book.setCurrentlyReading(true);
                            Intent intent = new Intent(BookActivity.this, CurrentlyReadingActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        performActivity(
                                Objects.requireNonNull(setActivity(book)),
                                ParentActivity.CURRENTLYREADING,
                                book,
                                "currently reading"
                        );
                    }
                }
            });
        }
    }


    /**
     * @param fromActivity This is the activity from which the book is deleted
     * @param toActivity   This is the activity to which the book is added
     * @param book
     * @param toLocation     (which activity) ?
     *                     ex: (currently reading) ?
     */
    private void performActivity(ParentActivity fromActivity, ParentActivity toActivity, Book book, String toLocation) {
        AlertDialog.Builder builder = new AlertDialog.Builder(BookActivity.this);
        System.out.println(fromActivity);
        switch (fromActivity) {
            case WANTTOREAD:
//                want to read
                builder.setMessage("Are you sure you want to move this book from want to read to " + toLocation.concat(" ?"));
                builder.setPositiveButton("Yes", (dialog, which) -> {
                            Utils.getInstance(BookActivity.this).deleteWantToReadBook(book);
                            btnAddToWantToRead.setEnabled(true);

                            toActivityPerformed(toActivity, book);

                  /*  Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book);
                    btnAddToCurrentlyReading.setEnabled(false);*/

                            completeMoved(book, toLocation, toActivity);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
                break;
            case ALREADYREAD:
                builder.setMessage("Are you sure you want to move this book from already read to " + toLocation.concat(" ?"));
                builder.setPositiveButton("Yes", (dialog, which) -> {
                            Utils.getInstance(BookActivity.this).deleteAlreadyReadBook(book);
                            btnAddToAlreadyRead.setEnabled(true);

                            toActivityPerformed(toActivity, book);

                    /*Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book);
                    btnAddToCurrentlyReading.setEnabled(false);*/

                            completeMoved(book, toLocation, toActivity);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
                break;
            case CURRENTLYREADING:
                builder.setMessage("Are you sure you want to move this book from currently reading to " + toLocation.concat(" ?"));
                builder.setPositiveButton("Yes", (dialog, which) -> {
                            Utils.getInstance(BookActivity.this).deleteCurrentlyReadingBook(book);
                            btnAddToCurrentlyReading.setEnabled(true);
                            toActivityPerformed(toActivity, book);
                            completeMoved(book, toLocation, toActivity);
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                        .create()
                        .show();
                break;
            default:
                Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * @param toActivity
     * @param book
     */
    private void toActivityPerformed(ParentActivity toActivity, Book book) {
        switch (toActivity) {
            case CURRENTLYREADING:
                Utils.getInstance(BookActivity.this).addToCurrentlyReadingBooks(book);
                btnAddToCurrentlyReading.setEnabled(false);
                break;
            case ALREADYREAD:
                Utils.getInstance(BookActivity.this).addToAlreadyReadBooks(book);
                btnAddToAlreadyRead.setEnabled(false);
                break;
            case WANTTOREAD:
                Utils.getInstance(BookActivity.this).addToWantToReadBooks(book);
                btnAddToWantToRead.setEnabled(false);
                break;
            default:
                Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * @param book
     */
    private void completeMoved(Book book, String location, ParentActivity activity) {
        Class<?> context = null;
        switch (activity) {
            case CURRENTLYREADING:
                context = CurrentlyReadingActivity.class;
                break;
            case ALREADYREAD:
                context = AlreadyReadActivity.class;
                break;
            case WANTTOREAD:
                context = WantToReadActivity.class;
                break;
        }
        Intent intent = new Intent(BookActivity.this, context);
        startActivity(intent);
        book.setCurrentlyReading(true);
        Toast.makeText(BookActivity.this, "Book added to "+location, Toast.LENGTH_SHORT).show();
    }

    /**
     * Set ParentActivity based on book status to help with move book to another cart
     *
     * @param book
     * @return
     */
    @Nullable
    private static ParentActivity setActivity(Book book) {
        return book.isWantToRead() ? ParentActivity.WANTTOREAD :
                book.isAlreadyRead() ? ParentActivity.ALREADYREAD :
                        book.isCurrentlyReading() ? ParentActivity.CURRENTLYREADING : null;
    }

    /**
     * Check the book status and return true if valid to move to another cart based on book status
     *
     * @param book
     * @param activity state of the incoming card
     * @return
     */
    private boolean validateCarts(Book book, ParentActivity activity) {

        switch (activity) {
            case CURRENTLYREADING:
                return !(book.isAlreadyRead() | book.isWantToRead());
            case WANTTOREAD:
                return !(book.isAlreadyRead() | book.isCurrentlyReading());
            case ALREADYREAD:
                return !(book.isWantToRead() | book.isCurrentlyReading());
            default:
                return false;
        }
    }

    /**
     * Enable or disable button based on book status
     * Add the book to already read arraylist
     *
     * @param book
     */
    private void isAlreadyReadBook(Book book) {
        AtomicBoolean isRead = new AtomicBoolean(false);
        Utils.getInstance(this).getAlreadyReadBooks().forEach(alreadyReadBook -> {
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
                    if (validateCarts(book, ParentActivity.ALREADYREAD)) {
                        if (Utils.getInstance(BookActivity.this).addToAlreadyReadBooks(book)) {
                            Toast.makeText(BookActivity.this, "Book added to already read", Toast.LENGTH_SHORT).show();
                            btnAddToAlreadyRead.setEnabled(false);
                            Intent intent = new Intent(BookActivity.this, AlreadyReadActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(BookActivity.this, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        performActivity(
                                Objects.requireNonNull(setActivity(book)),
                                ParentActivity.ALREADYREAD,
                                book,
                                "already read"
                        );
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
        btnUpdateBook = findViewById(R.id.btnUpdateBook);
        btnDeleteBook = findViewById(R.id.btnDeleteBook);
        txtViewName = findViewById(R.id.txtName);
        txtViewAuthor = findViewById(R.id.txtViewAuthor);
        txtViewLongDesc = findViewById(R.id.txtViewLongDesc);
        txtViewPages = findViewById(R.id.txtViewPages);
        imageViewBook = findViewById(R.id.imgBook);
        btnAddToCurrentlyReading = findViewById(R.id.btnAddToCurrentlyReading);
        btnAddToWantToRead = findViewById(R.id.btnAddToWantToRead);
        btnAddToAlreadyRead = findViewById(R.id.btnAddToAlreadyRead);
        btnAddToFavorite = findViewById(R.id.btnAddToFavorite);
    }



    @Override
    public void onDelete(String favoriteBooks, boolean b) {
        Toast.makeText(this, "Book deleted", Toast.LENGTH_SHORT).show();
    }

}