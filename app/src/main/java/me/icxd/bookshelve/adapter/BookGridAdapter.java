package me.icxd.bookshelve.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import me.icxd.bookshelve.R;
import me.icxd.bookshelve.activity.BookInfoActivity;
import me.icxd.bookshelve.app.MyApplication;
import me.icxd.bookshelve.model.bean.Book;

/**
 * Created by HaPBoy on 5/15/16.
 */
public class BookGridAdapter extends BaseAdapter {

    private List<Book> list;
    private LayoutInflater inflater;
    Context context;

    public BookGridAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Book> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.fragment_book_grid_item, null);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.rbRate = (RatingBar) convertView.findViewById(R.id.rb_rate);
            viewHolder.tvRate = (TextView) convertView.findViewById(R.id.tv_rate);
            convertView.setTag(viewHolder);
        }

        viewHolder = (ViewHolder) convertView.getTag();
        Book bean = list.get(position);

        // 设置图片
        Glide.with(viewHolder.ivCover.getContext())
                .load(bean.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(viewHolder.ivCover.getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).color(Color.GRAY).paddingDp(10))
                .into(viewHolder.ivCover);

        // 设置其他
        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.rbRate.setRating((Float.parseFloat(bean.getAverage())/2));
        viewHolder.tvRate.setText(bean.getAverage());

        return convertView;
    }
}

class ViewHolder {
    public ImageView ivCover;
    public TextView tvTitle;
    public RatingBar rbRate;
    public TextView tvRate;
}
