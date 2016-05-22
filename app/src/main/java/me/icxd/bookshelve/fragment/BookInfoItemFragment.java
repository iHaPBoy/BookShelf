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

public class BookInfoItemFragment extends Fragment {
    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";
    private Book mBook;
    private List<Map<String, Object>> data;

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
                mBook = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                mBook = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView lv = (ListView) inflater.inflate(R.layout.fragment_book_info_item_list, container, false);

        // 数据
        data = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("tag", "作者");
        map.put("content", mBook.getAuthor());
        data.add(map);

        Map<String, Object> map1 = new HashMap<>();
        map1.put("tag", "出版社");
        map1.put("content", mBook.getPublisher());
        data.add(map1);

        Map<String, Object> map2 = new HashMap<>();
        map2.put("tag", "评分");
        map2.put("content", mBook.getAverage());
        data.add(map2);

        Map<String, Object> map3 = new HashMap<>();
        map3.put("tag", "出版年");
        map3.put("content", mBook.getPubdate());
        data.add(map3);

        Map<String, Object> map4 = new HashMap<>();
        map4.put("tag", "定价");
        map4.put("content", mBook.getPrice());
        data.add(map4);

        Map<String, Object> map5 = new HashMap<>();
        map5.put("tag", "ISBN");
        map5.put("content", mBook.getIsbn13());
        data.add(map5);

        // 列表适配器
        BookInfoAdapter lvBaseAdapter = new BookInfoAdapter(getActivity(), data);

        // 列表
        lv.setAdapter(lvBaseAdapter);

        return lv;
    }
}
