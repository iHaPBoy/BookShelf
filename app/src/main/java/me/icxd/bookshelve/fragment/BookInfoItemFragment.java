package me.icxd.bookshelve.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.icxd.bookshelve.adapter.BookInfoAdapter;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.model.bean.TagItem;

/**
 * Created by HaPBoy on 5/18/16.
 */
public class BookInfoItemFragment extends Fragment {
    
    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";
    
    private Book book;
    private List<TagItem> data;

    public static BookInfoItemFragment newInstance(int itemId) {
        BookInfoItemFragment fragment = new BookInfoItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookInfoItemFragment newInstance(Book book) {
        BookInfoItemFragment fragment = new BookInfoItemFragment();
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
        ListView lv = (ListView) inflater.inflate(R.layout.fragment_book_info_item_list, container, false);

        // 数据
        data = new ArrayList<>();
        data.add(new TagItem("作者", book.getAuthor()));
        data.add(new TagItem("出版社", book.getPublisher()));
        data.add(new TagItem("评分", book.getAverage()));
        data.add(new TagItem("出版年", book.getPubdate()));
        data.add(new TagItem("定价", book.getPrice()));
        data.add(new TagItem("ISBN", book.getIsbn13()));

        // 列表适配器
        BookInfoAdapter lvBaseAdapter = new BookInfoAdapter(getContext(), data);

        // 列表
        lv.setAdapter(lvBaseAdapter);

        return lv;
    }
}
