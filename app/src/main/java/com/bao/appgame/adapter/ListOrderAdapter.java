package com.bao.appgame.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bao.appgame.R;
import com.bao.appgame.activity.ReviewActivity;
import com.bao.appgame.model.ItemOrderList;

import java.util.ArrayList;

public class ListOrderAdapter extends BaseAdapter {

    private Context context;
    private int idlayout;
    private ArrayList<ItemOrderList> list;

    public ListOrderAdapter(Context context, int idlayout, ArrayList<ItemOrderList> list) {
        this.context = context;
        this.idlayout = idlayout;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(idlayout, parent, false);

            holder = new ViewHolder();
            holder.gameItem = convertView.findViewById(R.id.itemName2);
            holder.account = convertView.findViewById(R.id.account);
            holder.pass = convertView.findViewById(R.id.pass);
            holder.price = convertView.findViewById(R.id.price);
            holder.reviewIcon = convertView.findViewById(R.id.review);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemOrderList item = list.get(position);
        holder.gameItem.setText(item.getGamename());
        holder.account.setText(item.getAccount());
        holder.pass.setText(item.getPass());
        holder.price.setText(item.getPrice());

        holder.reviewIcon.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReviewActivity.class);
            intent.putExtra("gameName", item.getGamename());
            context.startActivity(intent);
        });

        return convertView;
    }

    private static class ViewHolder {
        TextView gameItem;
        TextView account;
        TextView pass;
        TextView price;
        ImageView reviewIcon;
    }
}
