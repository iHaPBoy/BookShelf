package me.icxd.bookshelve.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.model.bean.Book;

/**
 * Created by HaPBoy on 5/22/16.
 */
public class BookCoverFragment extends Fragment {

    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";

    private Book book;

    public static BookCoverFragment newInstance(int bookId) {
        BookCoverFragment fragment = new BookCoverFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookCoverFragment newInstance(Book book) {
        BookCoverFragment fragment = new BookCoverFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_BOOK_ID)) {
                book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);

        ImageView ivBookCover = (ImageView) view.findViewById(R.id.book_cover);
        ImageView ivBookCoverBg = (ImageView) view.findViewById(R.id.book_cover_bg);

        TextView tvRate = (TextView) view.findViewById(R.id.tv_cover_rate);
        RatingBar rbRate = (RatingBar) view.findViewById(R.id.rb_cover_rate);

        View viewRate = view.findViewById(R.id.book_rate);

        // 图书封面
        Glide.with(ivBookCover.getContext())
                .load(book.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_cover_icon))
                .error(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_cover_icon))
                .into(ivBookCover);

        // 背景
        Glide.with(ivBookCoverBg.getContext())
                .load(book.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(ivBookCoverBg.getContext(), 25, 3))
                .into(ivBookCoverBg);

        // 图书评分
        tvRate.setText(book.getAverage());
        rbRate.setRating((Float.parseFloat(book.getAverage())/2));

        // 图书封面入场动画
        Animation cover_an = AnimationUtils.loadAnimation(getContext(), R.anim.book_cover_anim);
        ivBookCover.startAnimation(cover_an);

        // 图书评分入场动画
        Animation rate_an = AnimationUtils.loadAnimation(getContext(), R.anim.book_cover_rate_anim);
        viewRate.startAnimation(rate_an);

        return view;
    }
}
