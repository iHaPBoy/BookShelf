package me.icxd.bookshelve.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import me.icxd.bookshelve.activity.BookInfoActivity;
import me.icxd.bookshelve.R;
import me.icxd.bookshelve.adapter.BookGridAdapter;
import me.icxd.bookshelve.model.bean.Book;

public class BookGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private BookGridAdapter bookGridAdapter;
    private int gridPosition;

    public static BookGridFragment newInstance() {
        BookGridFragment fragment = new BookGridFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);

        bookGridAdapter = new BookGridAdapter(DataSupport.findAll(Book.class), getContext());

        gridView.setAdapter(bookGridAdapter);
        gridView.setOnItemClickListener(this);
        registerForContextMenu(gridView);

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridPosition = position;
                return false;
            }
        });
        reloadData();
        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Toast.makeText(getContext(), "ID " + position, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong("id", bookGridAdapter.getItemId(position));
        intent.putExtras(bundle);
        startActivity(intent);
//        getActivity().overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);
    }

    public void reloadData() {
        bookGridAdapter.setData(DataSupport.findAll(Book.class));
        bookGridAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "删除选中");
        menu.add(1, 2, 1, "删除全部");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getItemId() == 1) {
            // 删除
            DataSupport.delete(Book.class, bookGridAdapter.getItemId(gridPosition));
            Toast.makeText(getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
            reloadData();
        } else {
            // 删除
            DataSupport.deleteAll(Book.class);
            reloadData();
        }
        return super.onContextItemSelected(item);
    }
}
