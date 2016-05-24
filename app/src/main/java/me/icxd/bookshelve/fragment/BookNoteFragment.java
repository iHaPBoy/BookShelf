package me.icxd.bookshelve.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.icxd.bookshelve.activity.BookNoteEditActivity;
import me.icxd.bookshelve.adapter.BookInfoAdapter;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.model.bean.Book;

/**
 * Created by HaPBoy on 5/18/16.
 */
public class BookNoteFragment extends Fragment {

    private static final String ARG_BOOK_ID = "book_id";
    private int booId = 1;

    private TextView tvContent;
    private TextView tvDate;
    private ImageView ivIconDate;

    public static BookNoteFragment newInstance(int itemId) {
        BookNoteFragment fragment = new BookNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            booId = getArguments().getInt(ARG_BOOK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentPanel = inflater.inflate(R.layout.fragment_book_note, container, false);

        tvContent = (TextView) contentPanel.findViewById(R.id.tv_content);
        tvDate = (TextView) contentPanel.findViewById(R.id.tv_date);
        ivIconDate = (ImageView) contentPanel.findViewById(R.id.iv_icon_date);

        // Edit Button
        ImageView ivEdit = (ImageView) contentPanel.findViewById(R.id.iv_edit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookNoteEditActivity.class);
                intent.putExtra("id", booId);
                startActivity(intent);
            }
        });

        return contentPanel;
    }

    public void loadData() {
        // 图书数据
        Book book = DataSupport.find(Book.class, booId);

        String note = book.getNote();
        String note_date = book.getNote_date();

        if (note.isEmpty()) {
            note = "\n暂无笔记，点击右上角的按钮开始写笔记吧！\n";
        }
        tvContent.setText(note);

        if (note_date.isEmpty()) {
            ivIconDate.setAlpha(0f);
            tvContent.setGravity(Gravity.CENTER);
        } else {
            ivIconDate.setAlpha(1f);
            tvContent.setGravity(Gravity.NO_GRAVITY);
        }
        tvDate.setText(note_date);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
