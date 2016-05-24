package me.icxd.bookshelve.activity;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.model.bean.Book;

/**
 * Created by HaPBoy on 5/21/16.
 */
public class BookNoteEditActivity extends BaseActivity {

    // Context
    private Context context;

    // Book
    private Book book;

    // 笔记输入框
    private EditText etNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_note_edit);

        // Context
        context = this;

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 图书ID
        int itemId = getIntent().getIntExtra("id", -1);

        // 图书Obj
        book = DataSupport.find(Book.class, itemId);

        // Activity标题
        setTitle("编辑笔记");

        // 笔记输入框
        etNote = (EditText) findViewById(R.id.et_note);

        // 显示笔记内容
        etNote.setText(book.getNote());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_save:
                book.setNote(etNote.getText().toString().trim());
                book.setNote_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                book.save();
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("保存成功")
                        .setContentText("笔记已保存")
                        .setConfirmText("确定")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                            }
                        })
                        .show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
