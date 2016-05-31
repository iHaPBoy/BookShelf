package me.icxd.bookshelve.activity;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.util.List;

import me.icxd.bookshelve.R;
import me.icxd.bookshelve.adapter.BookRecyclerAdapter;
import me.icxd.bookshelve.app.MyApplication;
import me.icxd.bookshelve.model.bean.Book;
import me.icxd.bookshelve.model.data.DataManager;

/**
 * Created by HaPBoy on 5/18/16.
 */
public class SearchActivity extends BaseActivity {

    public static int SEARCH_LOCAL = 0;
    public static int SEARCH_NET = 1;

    private int search_type = SEARCH_LOCAL;

    // 下拉刷新布局
    private SwipeRefreshLayout swipeRefreshLayout;

    // RecyclerView 线性布局管理器
    private LinearLayoutManager manager;

    // RecyclerView 数据适配器
    private BookRecyclerAdapter adapter;

    // 图书搜索结果总条数
    static int total = 10;

    // 搜索输入框
    private EditText etSearch;
    private String searchBookName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // 搜索类型
        search_type = getIntent().getIntExtra("search_type", SEARCH_NET);

        // 返回按钮
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // 去掉ActionBar阴影
        getSupportActionBar().setElevation(0);

        // Activity标题
        setTitle(search_type == SEARCH_LOCAL ? "查找" : "搜索");

        // 搜索输入框
        etSearch = (EditText) findViewById(R.id.et_search);
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                    click();
                    return true;
                }
                return false;
            }
        });

        // 搜索按钮
        ImageButton ibSearch = (ImageButton) findViewById(R.id.ib_search);
        ibSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click();
            }
        });

        // 初始化RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.fragment_search_book_recycler);
        manager = new LinearLayoutManager(this);
        adapter = new BookRecyclerAdapter(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        // 初始化下拉刷新控件
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.fragment_search_book_swipe);
        swipeRefreshLayout.setColorSchemeResources(R.color.google_blue, R.color.google_red, R.color.google_green, R.color.google_yellow);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                search();
            }
        });

        // 滑到底部加载更多
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();
                    int totalItemCount = manager.getItemCount();
                    if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
                        if (!swipeRefreshLayout.isRefreshing() && adapter.getItemCount() < total) {
                            swipeRefreshLayout.setRefreshing(true);
                            get();
                        }
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dx > 0) {
                    isSlidingToLast = false;
                } else {
                    isSlidingToLast = true;
                }
            }
        });
    }

    // 点击搜索
    private void click() {
        // 关闭软键盘
        closeKeyboard();

        // 搜索
        search();
    }

    // 执行搜索
    public void search() {
        if (!etSearch.getText().toString().trim().isEmpty()) {
            searchBookName = etSearch.getText().toString().trim().replace(" ", "\b");
            adapter.clear();
            adapter.notifyDataSetChanged();
            // 显示加载动画View
            if (findViewById(R.id.loadView).getVisibility() != View.VISIBLE) {
                findViewById(R.id.loadView).setVisibility(View.VISIBLE);
            }
            get();
        } else {
            Toast.makeText(this, "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    // 获取数据
    public void get() {
        if (search_type == SEARCH_LOCAL) {
            List<Book> books = DataSupport.where("title like ?", "%" + searchBookName + "%").find(Book.class);
            Log.i("SEARCH", "books.size: " + books.size());
            adapter.setData(books);
            adapter.notifyDataSetChanged();
            if (books.size() == 0) {
                Toast.makeText(MyApplication.getContext(), "找不到图书", Toast.LENGTH_SHORT).show();
            }

            // 隐藏加载动画View
            findViewById(R.id.loadView).setVisibility(View.GONE);
        } else if (search_type == SEARCH_NET) {
            if (adapter.getItemCount() < total) {
                DataManager.getBookSearch(searchBookName, adapter.getItemCount(), new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            total = response.getInt("total");
                            JSONArray array = response.getJSONArray("books");
                            Log.i("API", "total: " + total + ", books.size: " + array.length());
                            if (total == 0) {
                                Toast.makeText(MyApplication.getContext(), "找不到图书", Toast.LENGTH_SHORT).show();
                            }
                            for (int j = 0; j < array.length(); j++) {
                                Book data = DataManager.doubanBook2Book(DataManager.jsonObject2DoubanBook(array.getJSONObject(j)));
                                adapter.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);

                        // 隐藏加载动画View
                        findViewById(R.id.loadView).setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyApplication.getContext(), "搜索失败，请重试", Toast.LENGTH_SHORT).show();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        }
    }

    // 关闭软键盘
    private void closeKeyboard() {
        // 取消焦点
        etSearch.clearFocus();

        // 关闭输入法
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
