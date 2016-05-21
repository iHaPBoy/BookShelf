package me.icxd.bookshelve.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.icxd.bookshelve.R;
import me.icxd.bookshelve.fragment.BookGridFragment;
import me.icxd.bookshelve.fragment.MyFragment;
import me.icxd.bookshelve.model.bean.DoubanBook;
import me.icxd.bookshelve.model.data.DataManager;
import me.icxd.bookshelve.view.ViewPagerIndicator;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // ViewPager
    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;

    // ViewPagerIndicator
    private ViewPagerIndicator mViewPagerIndicator;
    private List<String> mTitles = Arrays.asList("全部", "收藏");

    // Fragment
    private List<Fragment> mContents = new ArrayList<Fragment>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("INFO", "onCreate");
        setContentView(R.layout.activity_main);

        // 顶部ToolBar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 右下角菜单
        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        fabMenu.setClosedOnTouchOutside(true);

        // 右下角按钮
        final FloatingActionButton fabBtnScanner = (FloatingActionButton) findViewById(R.id.fab_scanner);
        fabBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fabBtnAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Snackbar.make(view, "添加", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                String isbns[] = {"9787508658902", "9787518311293", "9787020105540"};
                for (String isbn : isbns) {
                    DataManager.getBookInfo(isbn, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            DoubanBook doubanBook = DataManager.jsonObject2DoubanBook(response);
                            DataManager.doubanBook2Book(doubanBook).save();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(MainActivity.this, "书籍不存在", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        // 左上角 Menu 按钮
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        // 菜单
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // ViewPager
        mViewPager = (ViewPager) findViewById(R.id.view_pager);

        // ViewPagerIndicator
        mViewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        mViewPagerIndicator.setTabItemTitles(mTitles);

        // Fragment
        mContents.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL));
        mContents.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE));

        // PagerAdapter
        mPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return mContents.size();
            }

            @Override
            public Fragment getItem(int position) { return mContents.get(position); }
        };

        mViewPager.setAdapter(mPagerAdapter);
        mViewPagerIndicator.setViewPager(mViewPager, 0);
//        mViewPager.setOffscreenPageLimit(0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
