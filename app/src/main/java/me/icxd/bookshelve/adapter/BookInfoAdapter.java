package me.icxd.bookshelve.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import me.icxd.bookshelve.R;

public class BookInfoAdapter extends BaseAdapter {
    private List<Map<String, Object>> lstData;
    private Activity context;

    public BookInfoAdapter(Activity context, List<Map<String, Object>> lstData) {
        this.context = context;
        this.lstData = lstData;
    }

    @Override
    public int getCount() {
        return lstData.size();
    }

    @Override
    public Object getItem(int position) {
        return lstData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;

        if (convertView == null) {
            convertView = context.getLayoutInflater().inflate(R.layout.fragment_book_info_item, null);
            holder = new ViewHolder();
            holder.tvTag = (TextView) convertView.findViewById(R.id.tag);
            holder.tvContent = (TextView) convertView.findViewById(R.id.content);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map<String, Object> data = lstData.get(position);
        String tag = data.get("tag").toString();
        if (tag == null) {
            tag = "";
        }
        String content = data.get("content").toString();
        if (content == null) {
            content = "";
        }
        holder.tvTag.setText(tag);
        holder.tvContent.setText(content);

        return convertView;
    }

    private final class ViewHolder {
        TextView tvTag;
        TextView tvContent;
    }
}
