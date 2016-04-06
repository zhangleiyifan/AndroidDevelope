package com.gyz.androiddevelope.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gyz.androiddevelope.R;

import java.util.List;

/**
 * @author: guoyazhou
 * @date: 2016-03-31 17:43
 */
public class ListAdapter extends BaseAdapter {
    private static final String TAG = "ListAdapter";

    private List<String> list;
    private Context context;

    public ListAdapter(Context context, List<String> list) {
        this.context = context;
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
        return position;
    }

    HoldView holdView = null;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            holdView = new HoldView();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list, null);
            holdView.revenue_date = (TextView) convertView.findViewById(R.id.txtItem);
            convertView.setTag(holdView);
        } else {
            holdView = (HoldView) convertView.getTag();
        }

        holdView.revenue_date.setText(list.get(position));
        return convertView;
    }

    class HoldView {
        TextView revenue_date;
    }
}
