package me.icxd.bookshelve.adapter;

import android.content.Context;
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

import java.util.List;

import me.icxd.bookshelve.R;
import me.icxd.bookshelve.app.MyApplication;
import me.icxd.bookshelve.model.bean.Book;

/**
 * Created by HaPBoy on 5/15/16.
 */
public class BookGridAdapter extends BaseAdapter {

    private List<Book> mData;
    private LayoutInflater mInflater;

    public BookGridAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<Book> mData) {
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
//        Log.e("HB_ADAPTER", "mData.size: " + mData.size() + "; position: " + position + ";");
//        return 0;
        return mData.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.fragment_book_grid_item, null);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.rbRate = (RatingBar) convertView.findViewById(R.id.rb_rate);
            viewHolder.tvRate = (TextView) convertView.findViewById(R.id.tv_rate);
            convertView.setTag(viewHolder);
        }
        Book bean = mData.get(position);
        viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.tvTitle.setText(bean.getTitle());
        viewHolder.rbRate.setRating((Float.parseFloat(bean.getAverage())/2));
        viewHolder.tvRate.setText(bean.getAverage());

        Glide.with(viewHolder.ivCover.getContext())
                .load(bean.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .animate(R.anim.image_in)
//                .placeholder(R.drawable.book1)
//                .error(R.mipmap.ic_launcher)
                .into(viewHolder.ivCover);

        return convertView;
    }

    class ViewHolder {
        public ImageView ivCover;
        public TextView tvTitle;
        public RatingBar rbRate;
        public TextView tvRate;
    }
}
