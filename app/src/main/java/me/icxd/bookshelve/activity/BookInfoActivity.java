package me.icxd.bookshelve.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.fragment.BookInfoItemFragment;
import me.icxd.bookshelve.fragment.BookNoteFragment;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.view.ViewPagerIndicator;

public class BookInfoActivity extends AppCompatActivity {
    // ViewPager
    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("基本信息", "我的笔记");

    // Fragment
    private List<Fragment> mContents = new ArrayList<Fragment>();

    private long itemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);

        // 图书ID
        itemId = getIntent().getExtras().getLong("id", 0);

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

        ImageView ivBookCover = (ImageView) findViewById(R.id.book_cover);
        ImageView ivBookCoverBg = (ImageView) findViewById(R.id.book_cover_bg);

        Book book = DataSupport.find(Book.class, itemId);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        setTitle(book.getTitle());

        if (book != null) {
            Glide.with(ivBookCover.getContext())
                    .load(book.getImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .animate(R.anim.image_in)
//                .placeholder(R.drawable.book1)
//                .error(R.mipmap.ic_launcher)
                    .into(ivBookCover);

            Glide.with(ivBookCoverBg.getContext())
                    .load(book.getImage())
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .bitmapTransform(new BlurTransformation(ivBookCoverBg.getContext(), 25, 3))
//                .animate(R.anim.image_in)
//                .placeholder(R.drawable.book1)
//                .error(R.mipmap.ic_launcher)
                    .into(ivBookCoverBg);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
