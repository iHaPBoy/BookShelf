package me.icxd.bookshelve.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.icxd.bookshelve.activity.BookNoteEditActivity;
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
        View contentPanel = inflater.inflate(R.layout.fragment_book_note, container, false);

        TextView tvContent = (TextView) contentPanel.findViewById(R.id.tv_content);
        TextView tvDate = (TextView) contentPanel.findViewById(R.id.tv_date);
        Button btnEdit = (Button) contentPanel.findViewById(R.id.btn_edit);

        // 图书数据
        Book book = DataSupport.find(Book.class, mItemId);

        String note = book.getNote();
        String note_date = book.getNote_date();
        if (note.isEmpty()) {
            note = "暂无笔记";
        }
        tvContent.setText(note);
        tvDate.setText(note_date);

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookNoteEditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putLong("item_id", mItemId);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        return contentPanel;
    }
}
