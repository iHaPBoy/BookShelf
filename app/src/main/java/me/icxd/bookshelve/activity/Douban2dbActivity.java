package me.icxd.bookshelve.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import me.icxd.bookshelve.R;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.model.bean.DoubanBook;
import me.icxd.bookshelve.model.data.DataManager;

public class Douban2dbActivity extends BaseActivity {

    private Context context;
    private Book bookData;
    private EditText editText;
    private boolean isAdded = false;
    private String isbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_douban2db);

        this.context = this;
        this.bookData = null;
        this.editText = (EditText) findViewById(R.id.result);

        editText.setText(getIntent().getStringExtra("ISBN"));

        Button btn_add = (Button) findViewById(R.id.btn_add);
        assert btn_add != null;
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isbn = editText.getText().toString();
                add();
            }
        });

        Button btn_back = (Button) findViewById(R.id.btn_back);
        assert btn_back != null;
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra("reload", true);
                startActivity(intent);
            }
        });
    }

    private void add() {
        DataManager.getBookInfoFromISBN(isbn, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                DoubanBook doubanBook = DataManager.jsonObject2DoubanBook(response);
                bookData = DataManager.doubanBook2Book(doubanBook);
                Log.i("BOOK", bookData.getTitle());
                saveBook(bookData);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("API", "[" + isbn + "] 图书不存在");
                Toast.makeText(context, "书籍不存在", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveBook(Book book) {
        //当前数据库中所有的书籍，用来判断是否已经添加过这本书
        List<Book> books = DataSupport.findAll(Book.class);
        for (int i = 0; i < books.size(); i++) {
            Book book_db = books.get(i);
            if ((book_db.getAuthor() + book_db.getTitle()).equals(book.getAuthor() + book.getTitle())) {
                isAdded = true;
                break;
            } else {
                isAdded = false;
            }
        }

        if (isAdded) {
            Log.i("INFO", "[" + book.getIsbn13() + " " + book.getTitle() + "] 已存在");
            Toast.makeText(context, "你已经添加过了哦～", Toast.LENGTH_SHORT).show();
        } else {
            if (book.save()) {
                Log.i("INFO", "[" + book.getIsbn13() + " " + book.getTitle() + "] 保存成功");
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("INFO", "[" + book.getIsbn13() + " " + book.getTitle() + "] 保存失败");
                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            editText.setText(data.getStringExtra("ISBN"));
        }
    }
}
