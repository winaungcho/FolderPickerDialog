package com.structsoftlab.folderpickerdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import android.widget.*;
import java.util.*;

public class MyCustomAdapter extends ArrayAdapter<Item> {
    LayoutInflater minflater;
    Context mcontext;
    public List<Item> mList;
    public MyCustomAdapter(Context context, int resource, List<Item> list) {
        super(context, resource, list);

        // TODO Auto-generated constructor stub
        mcontext = context;
        mList = list;
        minflater = LayoutInflater.from(context);
    }

    @Override

    public View getView(final int position, View view, ViewGroup parent) {
        final MyCustomAdapter.ViewHolder holder;
        if (view == null) {
            holder = new MyCustomAdapter.ViewHolder();
            view = minflater.inflate(R.layout.list_item, null);
            holder.name = (TextView) view.findViewById(R.id.text_item);
			holder.icon = (ImageView) view.findViewById(R.id.image_item);
			holder.subtitle = (TextView) view.findViewById(R.id.subtitle);
            view.setTag(holder);
        } else {
            holder = (MyCustomAdapter.ViewHolder) view.getTag();
        }
        holder.name.setText(mList.get(position).file.toString());
		{
			String substr = (String) android.text.format.DateFormat.format("yyyy-MM-dd hh:mm:ss a", mList.get(position).date);
			if (mList.get(position).date == 0) substr = "";
			holder.subtitle.setText(substr);
		}
			
		holder.icon.setImageResource(mList.get(position).icon);
        return view;

    }

    private class ViewHolder {
        TextView name;
		ImageView icon;
		TextView subtitle;
    }
}
