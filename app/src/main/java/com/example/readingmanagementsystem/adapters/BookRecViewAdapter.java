package com.example.readingmanagementsystem.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionManager;

import com.bumptech.glide.Glide;
import com.example.readingmanagementsystem.R;
import com.example.readingmanagementsystem.activity.BookActivity;
import com.example.readingmanagementsystem.activity.ParentActivity;
import com.example.readingmanagementsystem.model.Book;
import com.example.readingmanagementsystem.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {
    private static final String TAG = "BookRecViewAdapter";
    private Context context;
    private ParentActivity parentActivity;
    private ArrayList<Book> books = new ArrayList<>();

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public BookRecViewAdapter(Context context, ParentActivity parentActivity) {
        this.context = context;
        this.parentActivity = parentActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_books, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder called");
        holder.txtName.setText(books.get(position).getName());
        Glide.with(context).asBitmap().load(books.get(position).getImageUrl()).into(holder.imgBook);
        holder.txtAuthor.setText(books.get(position).getAuthor());
        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BookActivity.class);
                intent.putExtra("book", (Serializable) books.get(position));
                context.startActivity(intent);
            }
        });

        if (books.get(position).isExpanded()) {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedLayout.setVisibility(View.VISIBLE);
            holder.downArrow.setVisibility(View.GONE);
        } else {
            TransitionManager.beginDelayedTransition(holder.parent);
            holder.expandedLayout.setVisibility(View.GONE);
            holder.downArrow.setVisibility(View.VISIBLE);
        }

        switch (parentActivity) {
            case ALLBOOKS:
                holder.btnDelete.setVisibility(View.GONE);
                break;
            case FAVORITE:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to delete this book from favorites?")
                                .setPositiveButton("Yes", (dialog, id) -> {
                                    if (Utils.getInstance().deleteFavoriteBook(books.get(position))) {
                                        Toast.makeText(context, books.get(position).getName().concat(" is deleted from favorites"), Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, books.size());
                                    } else {
                                        Toast.makeText(context, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = builder.create();
                        alert.show();

                      /*  if (Utils.getInstance().deleteFavoriteBook(books.get(position))) {
                            Toast.makeText(context, "Book deleted from favorites", Toast.LENGTH_SHORT).show();
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, books.size());
                        } else {
                            Toast.makeText(context, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                        }*/
                    }
                });
                break;
            case CURRENTLYREADING:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to delete this book from currently reading?")
                                .setPositiveButton("Yes", (dialog, id) -> {
                                    if (Utils.getInstance().deleteCurrentlyReadingBook(books.get(position))) {
                                        Toast.makeText(context, books.get(position).getName().concat(" is deleted from currently reading"), Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, books.size());
                                    } else {
                                        Toast.makeText(context, "Something went wrong! Try again", Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
                break;
            case WANTTOREAD:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure you want to delete this book from want to read?")
                                .setPositiveButton("Yes", (dialog, id) -> {
                                    if (Utils.getInstance().deleteWantToReadBook(books.get(position))) {
                                        Toast.makeText(context, books.get(position).getName().concat(" is deleted from want to read"), Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, books.size());
                                    }
                                })
                                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
                    }
                });
                break;
            case ALREADYREAD:
                holder.btnDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage("Are you sure want to delete".concat(books.get(position).getName().concat(" from already read list ?")))
                                .setPositiveButton("Yes", ((dialog, which) -> {
                                    if (Utils.getInstance().deleteAlreadyReadBook(books.get(position))) {
                                        Toast.makeText(context, books.get(position).getName().concat(" is deleted from already read list"), Toast.LENGTH_SHORT).show();
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, books.size());
                                    }
                                }))
                                .setNegativeButton("No", (dialog, which) -> dialog.cancel());
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private ImageView imgBook;
        private TextView txtName;

        private ImageView upArrow;
        private ImageView downArrow;
        private RelativeLayout expandedLayout;
        private TextView txtAuthor;
        private TextView txtShortDesc;

        private Button btnDelete;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            imgBook = itemView.findViewById(R.id.imgBook);
            txtName = itemView.findViewById(R.id.txtBookName);
            upArrow = itemView.findViewById(R.id.btnUpArrow);
            downArrow = itemView.findViewById(R.id.btnDownArrow);
            expandedLayout = itemView.findViewById(R.id.expandedRelLayout);
            txtAuthor = itemView.findViewById(R.id.txtAuthor);
            txtShortDesc = itemView.findViewById(R.id.txtShortDesc);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            downArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

            upArrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Book book = books.get(getAdapterPosition());
                    book.setExpanded(!book.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

           /* btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    books.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });*/
        }
    }
}
