package me.icxd.bookshelve.activity;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
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
import me.icxd.bookshelve.fragment.BookNoteFragment;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.view.ViewPagerIndicator;

public class BookInfoActivity extends BaseActivity {

    private Context mContext;

    // ViewPager
    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("基本信息", "我的笔记");

    // Fragment
    private List<Fragment> mContents = new ArrayList<>();

    // Book
    private long itemId;
    private Book book;

    // FavoriteMenu
    private int iconFavorite[] = {R.drawable.ic_favorite_border_white_24dp, R.drawable.ic_favorite_white_24dp};

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        mContext = this;

        // 图书ID
        itemId = getIntent().getExtras().getLong("id", -1);

        // 图书Obj
        book = DataSupport.find(Book.class, itemId);

        // ViewPager
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // ViewPagerIndicator
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mViewPagerIndicator.setTabItemTitles(mTitles);

        // 基本信息 Fragment
        BookInfoItemFragment bookInfoItemFragment = BookInfoItemFragment.newInstance(itemId);
        mContents.add(bookInfoItemFragment);

        // 我的笔记 Fragment
        BookNoteFragment bookNoteFragment = BookNoteFragment.newInstance(itemId);
        mContents.add(bookNoteFragment);

        // PagerAdapter
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mContents.size();
            }

            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }
        };

        mViewPager.setAdapter(mPagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager, 0);

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Activity标题
        setTitle(book.getTitle());

        // 图书封面
        Fragment bookCoverragment = BookCoverFragment.newInstance(book.getImage());
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_book_cover, bookCoverragment).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_favorite:
                book.setFavourite(!book.isFavourite());
                book.save();
                invalidateOptionsMenu();
                new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(book.isFavourite() ? "收藏成功" : "取消收藏")
                        .setContentText(book.isFavourite() ? "图书已收藏" : "图书已取消收藏")
                        .setConfirmText("确定")
                        .show();
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
}
