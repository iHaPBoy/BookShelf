package me.icxd.bookshelve.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import me.icxd.bookshelve.R;
import me.icxd.bookshelve.activity.BookInfoAddActivity;
import me.icxd.bookshelve.model.bean.Book;

/**
 * Created by HaPBoy on 5/21/16.
 */
public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerViewHolder> {

    List<Book> list;
    LayoutInflater inflater;
    Context context;

    public BookRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void add(Book bookData) {
        list.add(bookData);
        notifyItemInserted(list.size() - 1);
    }

    public void clear() {
        list.clear();
    }

    @Override
    public BookRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookRecyclerViewHolder(inflater.inflate(R.layout.activity_search_book_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BookRecyclerViewHolder holder, final int position) {
        // 设置图片
        Glide.with(holder.bookImage.getContext())
                .load(list.get(position).getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(holder.bookImage.getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_item_icon).paddingDp(10))
                .into(holder.bookImage);

        // 设置其他
        holder.bookName.setText(list.get(position).getTitle());
        holder.bookPoints.setText(list.get(position).getAverage());
        holder.bookAuthor.setText(list.get(position).getAuthor());
        holder.bookPublisher.setText(list.get(position).getPublisher());
        holder.bookPubdate.setText(list.get(position).getPubdate());
        holder.bookPrice.setText(list.get(position).getPrice());

        // 设置翻译者
        if (list.get(position).getTranslator().isEmpty()) {
            holder.bookDivider.setVisibility(View.GONE);
            holder.bookTranslator.setText("");
        } else {
            holder.bookTranslator.setText(list.get(position).getTranslator() + " 译");
        }

        // 设置CardView点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BookInfoAddActivity.class);
                intent.putExtra("ISBN", list.get(position).getIsbn13());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class BookRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView bookImage;
    TextView bookName;
    TextView bookPoints;
    TextView bookAuthor;
    TextView bookTranslator;
    TextView bookPublisher;
    TextView bookPubdate;
    TextView bookPrice;
    TextView bookDivider;
    CardView cardView;

    public BookRecyclerViewHolder(View itemView) {
        super(itemView);
        bookImage = (ImageView) itemView.findViewById(R.id.book_item_image);
        bookName = (TextView) itemView.findViewById(R.id.book_item_title);
        bookPoints = (TextView) itemView.findViewById(R.id.book_item_points);
        bookAuthor = (TextView) itemView.findViewById(R.id.book_item_author);
        bookTranslator = (TextView) itemView.findViewById(R.id.book_item_translator);
        bookPublisher = (TextView) itemView.findViewById(R.id.book_item_publisher);
        bookPubdate = (TextView) itemView.findViewById(R.id.book_item_pubdate);
        bookPrice = (TextView) itemView.findViewById(R.id.book_item_price);
        bookDivider = (TextView) itemView.findViewById(R.id.book_item_divider);
        cardView = (CardView) itemView.findViewById(R.id.book_item);
    }
}