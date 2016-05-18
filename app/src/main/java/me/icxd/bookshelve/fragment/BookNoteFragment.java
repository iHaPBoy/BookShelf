package me.icxd.bookshelve.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.icxd.bookshelve.adapter.BookInfoAdapter;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.model.bean.Book;

public class BookNoteFragment extends Fragment {
    private long mItemId = 1;
    private static final String ARG_ITEM_ID = "item_id";
    private List<Map<String, Object>> data;

    public static BookNoteFragment newInstance(long itemId) {
        BookNoteFragment fragment = new BookNoteFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_ITEM_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mItemId = getArguments().getLong(ARG_ITEM_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.fragment_book_note, container, false);

        TextView tvContent = (TextView) linearLayout.findViewById(R.id.tv_content);

        // 图书数据
        Book book = DataSupport.find(Book.class, mItemId);

        String note = book.getNote();
        String note_date = book.getNote_date();
        if (note.isEmpty()) {
            note = "暂无笔记";
        }
        tvContent.setText(note);
        return linearLayout;
    }
}
