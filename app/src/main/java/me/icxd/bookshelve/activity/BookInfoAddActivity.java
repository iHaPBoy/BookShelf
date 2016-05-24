package me.icxd.bookshelve.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.fragment.BookCoverFragment;
import me.icxd.bookshelve.fragment.BookInfoItemFragment;
import me.icxd.bookshelve.fragment.MyFragment;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.model.bean.DoubanBook;
import me.icxd.bookshelve.model.data.DataManager;
import me.icxd.bookshelve.view.ViewPagerIndicator;

/**
 * Created by HaPBoy on 5/22/16.
 */
public class BookInfoAddActivity extends BaseActivity implements View.OnClickListener {

    // Context
    private Context context;

    // ViewPager
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("基本信息");

    // Fragment
    private List<Fragment> fragments = new ArrayList<>();

    // Book
    private Book book;

    // 保存按钮
    private Button btnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info_add);

        // Context
        context = this;

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Activity标题
        setTitle("图书详情");

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // ViewPagerIndicator
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);
        viewPagerIndicator.setVisibleTabCount(1);

        // 保存按钮
        btnAdd = (Button) findViewById(R.id.btn_add);

        // 传入的ISBN
        String isbn = getIntent().getStringExtra("ISBN");

        // API获取图书数据
        DataManager.getBookInfoFromISBN(isbn, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                findViewById(R.id.loadView).setVisibility(View.GONE);
                book = DataManager.doubanBook2Book(DataManager.jsonObject2DoubanBook(response));

                // 设置保存按钮监听器
                btnAdd.setOnClickListener(BookInfoAddActivity.this);

                // 基本信息 Fragment
                BookInfoItemFragment bookInfoItemFragment = BookInfoItemFragment.newInstance(book);
                fragments.add(bookInfoItemFragment);

                // PagerAdapter
                pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                    @Override
                    public int getCount() {
                        return fragments.size();
                    }

                    @Override
                    public Fragment getItem(int position) {
                        return fragments.get(position);
                    }
                };

                // 设置数据适配器
                viewPager.setAdapter(pagerAdapter);
                viewPagerIndicator.setViewPager(viewPager, 0);

                // Activity标题
                setTitle(book.getTitle());

                // 图书封面
                Fragment bookCoverragment = BookCoverFragment.newInstance(book.getImage());
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_book_cover, bookCoverragment).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "图书不存在或网络连接错误", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // 保存图书
    public void saveBook(Book book) {
        Boolean isAdded = false;

        // 遍历当前数据库中所有的书籍，用来判断是否已经添加过这本书
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
            Toast.makeText(context, "你已经添加过了哦～", Toast.LENGTH_SHORT).show();
        } else {
            if (book.save()) {
                Toast.makeText(context, "保存成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BookInfoActivity.class);
                intent.putExtra("id", book.getId());
                startActivity(intent);
            } else {
                Toast.makeText(context, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(book.getAlt()));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_info_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        menuItem.setVisible(false);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        saveBook(book);
    }
}
