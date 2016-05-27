package me.icxd.bookshelve.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.fragment.BookCoverFragment;
import me.icxd.bookshelve.fragment.BookInfoItemFragment;
import me.icxd.bookshelve.fragment.BookIntroFragment;
import me.icxd.bookshelve.fragment.BookNoteFragment;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.view.ViewPagerIndicator;

/**
 * Created by HaPBoy on 5/18/16.
 */
public class BookInfoActivity extends BaseActivity {

    // Context
    private Context context;

    // ViewPager
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("基本信息", "图书简介", "我的笔记");

    // Fragment
    private List<Fragment> fragments = new ArrayList<>();

    // Book
    private Book book;

    // 收藏按钮图片
    private int iconFavorite[] = {R.drawable.ic_favorite_border_white_24dp, R.drawable.ic_favorite_white_24dp};

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        // Context
        context = this;

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 图书ID
        int bookId = getIntent().getIntExtra("id", -1);

        // 图书Obj
        book = DataSupport.find(Book.class, bookId);

        // Activity标题
        setTitle(book.getTitle());

        // ViewPager
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        // ViewPagerIndicator
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);
        viewPagerIndicator.setVisibleTabCount(3);

        // 基本信息 Fragment
        fragments.add(BookInfoItemFragment.newInstance(bookId));

        // 图书简介 Fragment
        fragments.add(BookIntroFragment.newInstance(bookId));

        // 我的笔记 Fragment
        fragments.add(BookNoteFragment.newInstance(bookId));

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

        // 图书封面
        Fragment bookCoverragment = BookCoverFragment.newInstance(bookId);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_book_cover, bookCoverragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_favorite:
                book.setFavourite(!book.isFavourite());
                book.save();
                invalidateOptionsMenu();
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(book.isFavourite() ? "收藏成功" : "取消收藏")
                        .setContentText(book.isFavourite() ? "图书已收藏" : "图书已取消收藏")
                        .setConfirmText("确定")
                        .show();
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
        menuItem.setIcon(iconFavorite[book.isFavourite() ? 1 : 0]);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
