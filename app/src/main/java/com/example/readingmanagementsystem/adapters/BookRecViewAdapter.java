package com.example.readingmanagementsystem.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.readingmanagementsystem.model.Book;

import java.util.ArrayList;

public class BookRecViewAdapter extends RecyclerView.Adapter<BookRecViewAdapter.ViewHolder> {
    private static final String TAG = "BookRecViewAdapter";
    private Context context;
    private ArrayList<Book> books = new ArrayList<>();

    public void setBooks(ArrayList<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    public BookRecViewAdapter(Context context) {
        this.context = context;
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
                Toast.makeText(context, books.get(position).getName() + " selected", Toast.LENGTH_SHORT).show();
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
        }
    }
}
