package me.icxd.bookshelve.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

import me.icxd.bookshelve.R;

public class BookInfoAddActivity extends BaseActivity {
    private Context context;
    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info_add);

        context = this;
        isbn = getIntent().getStringExtra("ISBN");

        Toast.makeText(context, "ISBN: " + isbn, Toast.LENGTH_SHORT).show();
    }
}
